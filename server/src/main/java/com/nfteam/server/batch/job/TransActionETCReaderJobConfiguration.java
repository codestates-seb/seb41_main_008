package com.nfteam.server.batch.job;

import com.nfteam.server.batch.entity.CoinRankingEntity;
import com.nfteam.server.batch.repository.RankingReaderRepository;
import com.nfteam.server.batch.writer.CoinRankingWriter;
import com.nfteam.server.coin.entity.CoinType;
import com.nfteam.server.transaction.entity.TransAction;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Collections;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class TransActionETCReaderJobConfiguration {

    private static final int chunkSize = 50;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RankingReaderRepository rankingReaderRepository;
    private final CoinRankingWriter coinRankingWriter;

    @Bean
    public Job rankingETCReaderJob() throws Exception {
        return jobBuilderFactory
                .get("rankingETCReaderJob")
                .start(rankingETCReaderStep())
                .build();
    }

    @Bean
    @JobScope
    public Step rankingETCReaderStep() throws Exception {
        return stepBuilderFactory
                .get("rankingETCReaderStep")
                .<TransAction, CoinRankingEntity>chunk(chunkSize)
                .reader(rankingETCReader()) // 해당 코인의 24시간 내 모든 거래 기록을 읽어온다.
                .processor(rankingETCProcessor()) // 거래량 랭킹을 String 값으로 전환한다.
                .writer(coinRankingWriter) // 랭킹 테이블에 랭킹 리스트를 저장한다.
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<TransAction> rankingETCReader() {
        return new RepositoryItemReaderBuilder<TransAction>()
                .name("rankingETCReader")
                .repository(rankingReaderRepository)
                .methodName("getRankingByCoin")
                .pageSize(chunkSize)
                .arguments(LocalDateTime.now().minusHours(24), CoinType.ETC.getValue())
                .sorts(Collections.singletonMap("transId", Sort.Direction.ASC))
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<TransAction, CoinRankingEntity> rankingETCProcessor() {
        CoinRankingEntity coinRankingEntity = new CoinRankingEntity();
        coinRankingEntity.updateCriteria(CoinType.ETC.getValue());
        return transActions -> {
            coinRankingEntity.addString(transActions.getCollection().getCollectionId());
            return coinRankingEntity;
        };
    }

}
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
public class TransActionDOGEReaderJobConfiguration {

    private static final int chunkSize = 50;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RankingReaderRepository rankingReaderRepository;
    private final CoinRankingWriter coinRankingWriter;

    @Bean
    public Job rankingDOGEReaderJob() throws Exception {
        return jobBuilderFactory
                .get("rankingDOGEReaderJob")
                .start(rankingDOGEReaderStep())
                .build();
    }

    @Bean
    @JobScope
    public Step rankingDOGEReaderStep() throws Exception {
        return stepBuilderFactory
                .get("rankingDOGEReaderStep")
                .<TransAction, CoinRankingEntity>chunk(chunkSize)
                .reader(rankingDOGEReader()) // 해당 코인의 24시간 내 모든 거래 기록을 읽어온다.
                .processor(rankingDOGEProcessor()) // 거래량 랭킹을 String 값으로 전환한다.
                .writer(coinRankingWriter) // 랭킹 테이블에 랭킹 리스트를 저장한다.
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<TransAction> rankingDOGEReader() {
        return new RepositoryItemReaderBuilder<TransAction>()
                .name("rankingDOGEReader")
                .repository(rankingReaderRepository)
                .methodName("getRankingByCoin")
                .pageSize(chunkSize)
                .arguments(LocalDateTime.now().minusHours(24), CoinType.DOGE.getValue())
                .sorts(Collections.singletonMap("transId", Sort.Direction.ASC))
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<TransAction, CoinRankingEntity> rankingDOGEProcessor() {
        CoinRankingEntity coinRankingEntity = new CoinRankingEntity();
        coinRankingEntity.updateCriteria(CoinType.DOGE.getValue());
        return transActions -> {
            coinRankingEntity.addString(transActions.getCollection().getCollectionId());
            return coinRankingEntity;
        };
    }

}
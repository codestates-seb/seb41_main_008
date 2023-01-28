package com.nfteam.server.domain.ranking.batch.job;

import com.nfteam.server.domain.coin.entity.CoinType;
import com.nfteam.server.domain.ranking.batch.entity.CoinRankingEntity;
import com.nfteam.server.domain.ranking.batch.repository.RankingReaderRepository;
import com.nfteam.server.domain.ranking.batch.writer.CoinRankingWriter;
import com.nfteam.server.domain.transaction.entity.TransAction;
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
public class TransActionBTCReaderJobConfiguration {

    private static final int chunkSize = 50;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RankingReaderRepository rankingReaderRepository;
    private final CoinRankingWriter coinRankingWriter;

    public TransActionBTCReaderJobConfiguration(JobBuilderFactory jobBuilderFactory,
                                                StepBuilderFactory stepBuilderFactory,
                                                RankingReaderRepository rankingReaderRepository,
                                                CoinRankingWriter coinRankingWriter) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.rankingReaderRepository = rankingReaderRepository;
        this.coinRankingWriter = coinRankingWriter;
    }

    @Bean
    public Job rankingBTCReaderJob() throws Exception {
        return jobBuilderFactory
                .get("rankingBTCReaderJob")
                .start(rankingBTCReaderStep())
                .build();
    }

    @Bean
    @JobScope
    public Step rankingBTCReaderStep() throws Exception {
        return stepBuilderFactory
                .get("rankingBTCReaderStep")
                .<TransAction, CoinRankingEntity>chunk(chunkSize)
                .reader(rankingBTCReader()) // 해당 코인의 24시간 내 모든 거래 기록을 읽어온다.
                .processor(rankingBTCProcessor()) // 거래량 랭킹을 String 값으로 전환한다.
                .writer(coinRankingWriter) // 랭킹 테이블에 랭킹 리스트를 저장한다.
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<TransAction> rankingBTCReader() {
        return new RepositoryItemReaderBuilder<TransAction>()
                .name("rankingBTCReader")
                .repository(rankingReaderRepository)
                .methodName("getRankingByCoin")
                .pageSize(chunkSize)
                .arguments(LocalDateTime.now().minusHours(24), CoinType.BTC.getValue())
                .sorts(Collections.singletonMap("transId", Sort.Direction.ASC))
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<TransAction, CoinRankingEntity> rankingBTCProcessor() {
        CoinRankingEntity coinRankingEntity = new CoinRankingEntity();
        coinRankingEntity.updateCriteria(CoinType.BTC.getValue());
        return transActions -> {
            coinRankingEntity.addString(transActions.getCollection().getCollectionId());
            return coinRankingEntity;
        };
    }

}
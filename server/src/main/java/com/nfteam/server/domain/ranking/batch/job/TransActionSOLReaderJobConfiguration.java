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
public class TransActionSOLReaderJobConfiguration {

    private static final int chunkSize = 50;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RankingReaderRepository rankingReaderRepository;
    private final CoinRankingWriter coinRankingWriter;

    public TransActionSOLReaderJobConfiguration(JobBuilderFactory jobBuilderFactory,
                                                StepBuilderFactory stepBuilderFactory,
                                                RankingReaderRepository rankingReaderRepository,
                                                CoinRankingWriter coinRankingWriter) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.rankingReaderRepository = rankingReaderRepository;
        this.coinRankingWriter = coinRankingWriter;
    }

    @Bean
    public Job rankingSOLReaderJob() throws Exception {
        return jobBuilderFactory
                .get("rankingSOLReaderJob")
                .start(rankingSOLReaderStep())
                .build();
    }

    @Bean
    @JobScope
    public Step rankingSOLReaderStep() throws Exception {
        return stepBuilderFactory
                .get("rankingSOLReaderStep")
                .<TransAction, CoinRankingEntity>chunk(chunkSize)
                .reader(rankingSOLReader()) // 해당 코인의 24시간 내 모든 거래 기록을 읽어온다.
                .processor(rankingSOLProcessor()) // 거래량 랭킹을 String 값으로 전환한다.
                .writer(coinRankingWriter) // 랭킹 테이블에 랭킹 리스트를 저장한다.
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<TransAction> rankingSOLReader() {
        return new RepositoryItemReaderBuilder<TransAction>()
                .name("rankingSOLReader")
                .repository(rankingReaderRepository)
                .methodName("getRankingByCoin")
                .pageSize(chunkSize)
                .arguments(LocalDateTime.now().minusHours(24), CoinType.SOL.getValue())
                .sorts(Collections.singletonMap("transId", Sort.Direction.ASC))
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<TransAction, CoinRankingEntity> rankingSOLProcessor() {
        CoinRankingEntity coinRankingEntity = new CoinRankingEntity();
        coinRankingEntity.updateCriteria(CoinType.SOL.getValue());
        return transActions -> {
            coinRankingEntity.addString(transActions.getCollection().getCollectionId());
            return coinRankingEntity;
        };
    }

}
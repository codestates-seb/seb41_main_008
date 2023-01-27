package com.nfteam.server.batch.job;

import com.nfteam.server.batch.entity.TimeRankingEntity;
import com.nfteam.server.batch.repository.RankingReaderRepository;
import com.nfteam.server.batch.writer.TimeRankingWriter;
import com.nfteam.server.transaction.entity.TransAction;
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
public class TransAction1MReaderJobConfiguration {

    private static final int chunkSize = 50;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RankingReaderRepository rankingReaderRepository;
    private final TimeRankingWriter timeRankingWriter;

    public TransAction1MReaderJobConfiguration(JobBuilderFactory jobBuilderFactory,
                                               StepBuilderFactory stepBuilderFactory,
                                               RankingReaderRepository rankingReaderRepository,
                                               TimeRankingWriter timeRankingWriter) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.rankingReaderRepository = rankingReaderRepository;
        this.timeRankingWriter = timeRankingWriter;
    }

    @Bean
    public Job ranking1MReaderJob() throws Exception {
        return jobBuilderFactory
                .get("ranking1MReaderJob")
                .start(ranking1MReaderStep())
                .build();
    }

    @Bean
    @JobScope
    public Step ranking1MReaderStep() throws Exception {
        return stepBuilderFactory
                .get("ranking1MReaderStep")
                .<TransAction, TimeRankingEntity>chunk(chunkSize)
                .reader(ranking1MReader()) // 해당 시간대 범위의 모든 거래 기록을 읽어온다.
                .processor(ranking1MProcessor()) // 거래량 랭킹을 String 값으로 전환한다.
                .writer(timeRankingWriter) // 랭킹 테이블에 랭킹 리스트를 저장한다.
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<TransAction> ranking1MReader() {
        return new RepositoryItemReaderBuilder<TransAction>()
                .name("ranking1MReader")
                .repository(rankingReaderRepository)
                .methodName("getRankingByTime")
                .pageSize(chunkSize)
                .arguments(LocalDateTime.now().minusMonths(1))
                .sorts(Collections.singletonMap("transId", Sort.Direction.ASC))
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<TransAction, TimeRankingEntity> ranking1MProcessor() {
        TimeRankingEntity timeRankingEntity = new TimeRankingEntity();
        timeRankingEntity.updateCriteria("month");
        return transActions -> {
            timeRankingEntity.addString(transActions.getCollection().getCollectionId());
            return timeRankingEntity;
        };
    }

}
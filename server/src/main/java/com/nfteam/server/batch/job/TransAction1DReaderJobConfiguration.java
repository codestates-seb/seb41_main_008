package com.nfteam.server.batch.job;

import com.nfteam.server.batch.entity.TimeRankingEntity;
import com.nfteam.server.batch.repository.RankingReaderRepository;
import com.nfteam.server.batch.writer.TimeRankingWriter;
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
public class TransAction1DReaderJobConfiguration {

    private static final int chunkSize = 50;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RankingReaderRepository rankingReaderRepository;
    private final TimeRankingWriter timeRankingWriter;

    @Bean
    public Job ranking1DReaderJob() throws Exception {
        return jobBuilderFactory
                .get("ranking1DReaderJob")
                .start(ranking1DReaderStep())
                .build();
    }

    @Bean
    @JobScope
    public Step ranking1DReaderStep() throws Exception {
        return stepBuilderFactory
                .get("ranking1DReaderStep")
                .<TransAction, TimeRankingEntity>chunk(chunkSize)
                .reader(ranking1DReader()) // 해당 시간대 범위의 모든 거래 기록을 읽어온다.
                .processor(ranking1DProcessor()) // 거래량 랭킹을 String 값으로 전환한다.
                .writer(timeRankingWriter) // 랭킹 테이블에 랭킹 리스트를 저장한다.
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<TransAction> ranking1DReader() {
        return new RepositoryItemReaderBuilder<TransAction>()
                .name("ranking1DReader")
                .repository(rankingReaderRepository)
                .methodName("getRankingByTime")
                .pageSize(chunkSize)
                .arguments(LocalDateTime.now().minusDays(1))
                .sorts(Collections.singletonMap("transId", Sort.Direction.ASC))
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<TransAction, TimeRankingEntity> ranking1DProcessor() {
        TimeRankingEntity timeRankingEntity = new TimeRankingEntity();
        timeRankingEntity.updateCriteria("day");
        return transActions -> {
            timeRankingEntity.addString(transActions.getCollection().getCollectionId());
            return timeRankingEntity;
        };
    }

}
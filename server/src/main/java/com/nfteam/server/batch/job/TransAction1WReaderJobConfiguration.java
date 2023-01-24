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
public class TransAction1WReaderJobConfiguration {

    private static final int chunkSize = 50;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RankingReaderRepository rankingReaderRepository;
    private final TimeRankingWriter timeRankingWriter;

    @Bean
    public Job ranking1WReaderJob() throws Exception {
        return jobBuilderFactory
                .get("ranking1WReaderJob")
                .start(ranking1WReaderStep())
                .build();
    }

    @Bean
    @JobScope
    public Step ranking1WReaderStep() throws Exception {
        return stepBuilderFactory
                .get("ranking1WReaderStep")
                .<TransAction, TimeRankingEntity>chunk(chunkSize)
                .reader(ranking1WReader()) // 해당 시간대 범위의 모든 거래 기록을 읽어온다.
                .processor(ranking1WProcessor()) // 거래량 랭킹을 String 값으로 전환한다.
                .writer(timeRankingWriter) // 랭킹 테이블에 랭킹 리스트를 저장한다.
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<TransAction> ranking1WReader() {
        return new RepositoryItemReaderBuilder<TransAction>()
                .name("ranking1WReader")
                .repository(rankingReaderRepository)
                .methodName("getRankingByTime")
                .pageSize(chunkSize)
                .arguments(LocalDateTime.now().minusWeeks(1))
                .sorts(Collections.singletonMap("transId", Sort.Direction.ASC))
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<TransAction, TimeRankingEntity> ranking1WProcessor() {
        TimeRankingEntity timeRankingEntity = new TimeRankingEntity();
        timeRankingEntity.updateCriteria("week");
        return transActions -> {
            timeRankingEntity.addString(transActions.getCollection().getCollectionId());
            return timeRankingEntity;
        };
    }

}
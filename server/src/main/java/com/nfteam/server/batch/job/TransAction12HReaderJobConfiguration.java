package com.nfteam.server.batch.job;

import com.nfteam.server.batch.entity.Ranking12H;
import com.nfteam.server.batch.repository.RankingReaderRepository;
import com.nfteam.server.batch.writer.Ranking12HWriter;
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
public class TransAction12HReaderJobConfiguration {

    public static final String JOB_NAME = "ranking12HReaderJob";
    private static final String STEP_NAME = "ranking12HReaderStep";
    private static final int chunkSize = 10;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RankingReaderRepository rankingReaderRepository;
    private final Ranking12HWriter ranking12HWriter;

    @Bean
    public Job ranking12HReaderJob() throws Exception {
        return jobBuilderFactory
                .get(JOB_NAME)
                .start(ranking12HReaderStep())
                .build();
    }

    @Bean
    @JobScope
    public Step ranking12HReaderStep() throws Exception {
        return stepBuilderFactory
                .get(STEP_NAME)
                .<TransAction, Ranking12H>chunk(chunkSize)
                .reader(ranking12HReader()) // 해당 시간대 범위의 모든 거래 기록을 읽어온다.
                .processor(ranking12HProcessor()) // 거래량 랭킹을 String 값으로 전환한다.
                .writer(ranking12HWriter) // 랭킹 테이블에 15개 랭킹을 저장한다.
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<TransAction> ranking12HReader() {
        return new RepositoryItemReaderBuilder<TransAction>()
                .name("ranking12HReader")
                .repository(rankingReaderRepository)
                .methodName("getRankingByTime")
                .pageSize(chunkSize)
                .arguments(LocalDateTime.now().minusHours(1200))
                .sorts(Collections.singletonMap("transId", Sort.Direction.ASC))
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<TransAction, Ranking12H> ranking12HProcessor() {
        Ranking12H ranking12H = new Ranking12H();
        return transActions -> {
            ranking12H.addString(transActions.getCollection().getCollectionId());
            return ranking12H;
        };
    }

}
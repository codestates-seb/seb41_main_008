package com.nfteam.server.domain.ranking.batch.job;

import com.nfteam.server.domain.ranking.batch.entity.TimeRankingEntity;
import com.nfteam.server.domain.ranking.batch.repository.RankingReaderRepository;
import com.nfteam.server.domain.ranking.batch.writer.TimeRankingWriter;
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
public class TransAction1WReaderJobConfiguration {

    private static final int chunkSize = 10;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RankingReaderRepository rankingReaderRepository;
    private final TimeRankingWriter timeRankingWriter;

    public TransAction1WReaderJobConfiguration(JobBuilderFactory jobBuilderFactory,
                                               StepBuilderFactory stepBuilderFactory,
                                               RankingReaderRepository rankingReaderRepository,
                                               TimeRankingWriter timeRankingWriter) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.rankingReaderRepository = rankingReaderRepository;
        this.timeRankingWriter = timeRankingWriter;
    }

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
                .reader(ranking1WReader()) // ?????? ????????? ????????? ?????? ?????? ????????? ????????????.
                .processor(ranking1WProcessor()) // ????????? ????????? String ????????? ????????????.
                .writer(timeRankingWriter) // ?????? ???????????? ?????? ???????????? ????????????.
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
package com.nfteam.server.batch.job;

import com.nfteam.server.batch.entity.Ranking6H;
import com.nfteam.server.batch.repository.Ranking6HRepository;
import com.nfteam.server.transaction.entity.TransAction;
import com.nfteam.server.transaction.repository.TransActionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class TransAction6HReaderJobConfiguration {

    public static final String JOB_NAME = "ranking6HReaderJob";
    private static final String STEP_NAME = "ranking6HReaderStep";
    private static final int chunkSize = 15;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final TransActionRepository transActionRepository;
    private final Ranking6HRepository ranking6HRepository;

    private List<Long> rankList = new ArrayList<>();

    @Bean
    public Job ranking6HReaderJob() throws Exception {
        return jobBuilderFactory
                .get(JOB_NAME)
                .start(ranking6HReaderStep())
                .build();
    }

    @Bean
    @JobScope
    public Step ranking6HReaderStep() throws Exception {
        return stepBuilderFactory
                .get(STEP_NAME)
                .<TransAction,List<Long>>chunk(chunkSize)
                .reader(ranking6HReader()) // 해당 시간대 범위의 모든 거래 기록을 읽어온다.
                .processor(ranking6HProcessor()) // 거래 기록 중 랭킹 상위 15개를 구한다.
                .writer(ranking6HWriter()) // 랭킹 테이블에 저장한다.
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<TransAction> ranking6HReader() {
        return new RepositoryItemReaderBuilder<TransAction>()
                .repository(transActionRepository)
                .methodName("findByCreatedDateAfter")
                .pageSize(chunkSize)
                .arguments(LocalDateTime.now().minusHours(80))
                .sorts(Collections.singletonMap("createdDate", Sort.Direction.ASC))
                .name("ranking6HReader")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<TransAction, List<Long>> ranking6HProcessor() {
        return transActions -> {
            rankList.add(transActions.getCollection().getCollectionId());
            return rankList;
        };
    }

    @Bean
    @StepScope
    public RepositoryItemWriter<List<Long>> ranking6HWriter() {

        while(rankList.size() < 15){
            rankList.add(99L);
        }

        ranking6HRepository.save(new Ranking6H(rankList));
        return null;
    }


}

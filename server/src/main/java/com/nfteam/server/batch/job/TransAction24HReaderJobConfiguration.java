package com.nfteam.server.batch.job;

import com.nfteam.server.transaction.entity.TransAction;
import com.nfteam.server.transaction.repository.TransActionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TransAction24HReaderJobConfiguration {
    private final TransActionRepository transActionRepository;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private static final int chunkSize = 10;

    @Bean
    public Job transActionRankingReaderJob() throws Exception {
        return jobBuilderFactory.get("transActionRankingReaderJob")
                .start(transActionRankingReaderStep())
                .build();
    }

    @Bean
    @JobScope
    public Step transActionRankingReaderStep() throws Exception {
        return stepBuilderFactory.get("transActionRankingReaderStep")
                .<TransAction, TransAction>chunk(chunkSize)
                .reader(transActionRankingReader()) // 시간대 내의 모든 거래 기록을 읽어온다.
                .processor(transActionRankingProcessor()) // 거래 기록 중 랭킹을 구한다.
                .writer(transActionRankingWriter()) // 랭킹 테이블에 저장한다.
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<TransAction> transActionRankingReader() {
        return new RepositoryItemReaderBuilder<TransAction>()
                .repository(transActionRepository)
                .methodName("findTransActionByCreatedDateAfter")
                .arguments(LocalDateTime.now().minusHours(6))
                .pageSize(chunkSize)
                .name("transActionRankingReader")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<TransAction, TransAction> transActionRankingProcessor() {
        return TransAction -> {

            return null;
        };
    }

    @Bean
    @StepScope
    public RepositoryItemWriter<TransAction> transActionRankingWriter() {
        return new RepositoryItemWriterBuilder<TransAction>()
                .repository(transActionRepository)
                .build();
    }


}

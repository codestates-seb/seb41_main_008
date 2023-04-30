package com.nfteam.server.domain.ranking.job;

import com.nfteam.server.domain.item.entity.ItemCollection;
import com.nfteam.server.domain.ranking.entity.DailyAggregate;
import com.nfteam.server.domain.ranking.repository.DailyAggregateRepository;
import com.nfteam.server.domain.transaction.entity.TransAction;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class TransActionDailyAggregateJobConfiguration {

    private static final int chunkSize = 10;
    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DailyAggregateRepository dailyAggregateRepository;

    @Bean
    public Job transactionDailyReaderJob() throws Exception {
        return jobBuilderFactory
                .get("transactionDailyReaderJob")
                .start(transactionDailyReaderStep())
                .build();
    }

    @Bean
    @JobScope
    public Step transactionDailyReaderStep() throws Exception {
        return stepBuilderFactory
                .get("transactionDailyReaderStep")
                .<TransAction, HashMap<Long, DailyAggregate>>chunk(chunkSize)
                .reader(transactionDailyReader()) // 하루 간 모든 거래 기록을 읽어온다.
                .processor(transactionDailyProcessor()) // 거래량을 컬렉션 별 HashMap 저장한다.
                .writer(transactionDailyWriter()) // 집계 테이블에 집계 정보를 저장한다.
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<TransAction> transactionDailyReader() {
        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("date", LocalDateTime.now().minusHours(24));

        JpaPagingItemReader<TransAction> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setParameterValues(parameters);
        reader.setQueryString("select t From TransAction t where t.createdDate >:date order by t.transId");
        reader.setPageSize(chunkSize);

        return reader;
    }

    @Bean
    @StepScope
    public ItemProcessor<TransAction, HashMap<Long, DailyAggregate>> transactionDailyProcessor() {
        HashMap<Long, DailyAggregate> map = new HashMap<>();
        return transActions -> {
            ItemCollection collection = transActions.getCollection();
            DailyAggregate dailyAggregate = map.getOrDefault(collection.getCollectionId(),
                    new DailyAggregate(collection, collection.getCoin(), LocalDate.now(), 0L, 0.0));

            dailyAggregate.addTotalVolume(); // 거래량 증가
            dailyAggregate.addTotalTradingPrice(transActions.getTransPrice()); // 거래금액 추가
            map.put(collection.getCollectionId(), dailyAggregate);

            return map;
        };
    }

    public ItemWriter<HashMap<Long, DailyAggregate>> transactionDailyWriter() {
        return items -> {
            HashMap<Long, DailyAggregate> aggregateHashMap = items.get(0);
            for (DailyAggregate da : aggregateHashMap.values()) {
                dailyAggregateRepository.save(da); // 각 컬렉션 별 집계정보 저장
            }
        };
    }

}
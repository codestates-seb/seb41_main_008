package com.nfteam.server.domain.ranking.scheduler;

import com.nfteam.server.domain.ranking.job.TransActionDailyAggregateJobConfiguration;
import com.nfteam.server.domain.ranking.service.RankingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class TransActionAggregateJobScheduler {

    private final JobLauncher jobLauncher;
    private final TransActionDailyAggregateJobConfiguration transActionDailyAggregateJobConfiguration;

    private final RankingService rankingService;

    public TransActionAggregateJobScheduler(JobLauncher jobLauncher, TransActionDailyAggregateJobConfiguration transActionDailyAggregateJobConfiguration, RankingService rankingService) {
        this.jobLauncher = jobLauncher;
        this.transActionDailyAggregateJobConfiguration = transActionDailyAggregateJobConfiguration;
        this.rankingService = rankingService;
    }

    // 일간 집계 배치 : 매일 오전 1시
    @Scheduled(cron = "0 0 1 1/1 * ?")
    public void run1DRankJob() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);

        try {
            jobLauncher.run(transActionDailyAggregateJobConfiguration.transactionDailyReaderJob(), jobParameters);
            // 이전 캐시정보 전체 삭제
            deleteCacheInfo();
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error("일간 집계 배치 에러 : {} / 날짜 : {}", e.getMessage(), LocalDateTime.now());
        } catch (Exception e) {
            log.error("일간 집계 배치 에러 : {} / 날짜 : {}", e.getMessage(), LocalDateTime.now());
        }
    }

    private void deleteCacheInfo() {
        rankingService.deleteTimeRankingCache("day");
        rankingService.deleteTimeRankingCache("week");
        rankingService.deleteTimeRankingCache("month");

        rankingService.deleteCoinRankingCache(1L);
        rankingService.deleteCoinRankingCache(2L);
        rankingService.deleteCoinRankingCache(3L);
        rankingService.deleteCoinRankingCache(4L);
        rankingService.deleteCoinRankingCache(5L);
    }

}
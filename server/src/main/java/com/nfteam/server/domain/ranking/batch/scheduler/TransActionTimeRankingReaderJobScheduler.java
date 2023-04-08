package com.nfteam.server.domain.ranking.batch.scheduler;

import com.nfteam.server.domain.ranking.batch.job.TransAction1DReaderJobConfiguration;
import com.nfteam.server.domain.ranking.batch.job.TransAction1MReaderJobConfiguration;
import com.nfteam.server.domain.ranking.batch.job.TransAction1WReaderJobConfiguration;
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
public class TransActionTimeRankingReaderJobScheduler {

    private final JobLauncher jobLauncher;
    private final RankingService rankingService;

    private final TransAction1DReaderJobConfiguration transAction1DReaderJobConfiguration;
    private final TransAction1WReaderJobConfiguration transAction1WReaderJobConfiguration;
    private final TransAction1MReaderJobConfiguration transAction1MReaderJobConfiguration;

    public TransActionTimeRankingReaderJobScheduler(JobLauncher jobLauncher,
                                                    RankingService rankingService,
                                                    TransAction1DReaderJobConfiguration transAction1DReaderJobConfiguration,
                                                    TransAction1WReaderJobConfiguration transAction1WReaderJobConfiguration,
                                                    TransAction1MReaderJobConfiguration transAction1MReaderJobConfiguration) {
        this.jobLauncher = jobLauncher;
        this.rankingService = rankingService;
        this.transAction1DReaderJobConfiguration = transAction1DReaderJobConfiguration;
        this.transAction1WReaderJobConfiguration = transAction1WReaderJobConfiguration;
        this.transAction1MReaderJobConfiguration = transAction1MReaderJobConfiguration;
    }

    // 일간 랭킹 배치 : 매일 오전 1시
    @Scheduled(cron = "0 0 1 1/1 * ?")
    public void run1DRankJob() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);

        try {
            jobLauncher.run(transAction1DReaderJobConfiguration.ranking1DReaderJob(), jobParameters);
            // 이전 캐시 정보 삭제
            rankingService.deleteTimeRankingCache("day");
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error("일간 랭킹 배치 에러 : {} / 날짜 : {}", e.getMessage(), LocalDateTime.now());
        } catch (Exception e) {
            log.error("일간 랭킹 배치 에러 : {} / 날짜 : {}", e.getMessage(), LocalDateTime.now());
        }
    }

    // 주간 랭킹 배치 : 매주 일요일 오전 2시
    @Scheduled(cron = "0 0 2 * * SUN")
    public void run1WRankJob() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);

        try {
            jobLauncher.run(transAction1WReaderJobConfiguration.ranking1WReaderJob(), jobParameters);
            // 이전 캐시 정보 삭제
            rankingService.deleteTimeRankingCache("week");
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error("주간 랭킹 배치 에러 : {} / 날짜 : {}", e.getMessage(), LocalDateTime.now());
        } catch (Exception e) {
            log.error("주간 랭킹 배치 에러 : {} / 날짜 : {}", e.getMessage(), LocalDateTime.now());
        }
    }

    // 월간 랭킹 배치 : 매월 마지막날 오전 3시
    @Scheduled(cron = "0 0 3 L * *")
    public void run1MRankJob() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);

        try {
            jobLauncher.run(transAction1MReaderJobConfiguration.ranking1MReaderJob(), jobParameters);
            // 이전 캐시 정보 삭제
            rankingService.deleteTimeRankingCache("month");
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error("월간 랭킹 배치 에러 : {} / 날짜 : {}", e.getMessage(), LocalDateTime.now());
        } catch (Exception e) {
            log.error("월간 랭킹 배치 에러 : {} / 날짜 : {}", e.getMessage(), LocalDateTime.now());
        }
    }

}
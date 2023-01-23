package com.nfteam.server.batch.scheduler;

import com.nfteam.server.batch.job.TransAction1DReaderJobConfiguration;
import com.nfteam.server.batch.job.TransAction1MReaderJobConfiguration;
import com.nfteam.server.batch.job.TransAction1WReaderJobConfiguration;
import com.nfteam.server.batch.job.TransActionSOLReaderJobConfiguration;
import lombok.RequiredArgsConstructor;
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

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransActionTimeRankingReaderJobScheduler {

    private final JobLauncher jobLauncher;
    private final TransAction1DReaderJobConfiguration transAction1DReaderJobConfiguration;
    private final TransAction1WReaderJobConfiguration transAction1WReaderJobConfiguration;
    private final TransAction1MReaderJobConfiguration transAction1MReaderJobConfiguration;

    // 일간 랭킹 배치 : 매일 오전 1시
    @Scheduled(cron = "0 0 1 1/1 * ?") // 초 분 시 일 월 요일
    public void run1DRankJob() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);

        try {
            jobLauncher.run(transAction1DReaderJobConfiguration.ranking1DReaderJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 주간 랭킹 배치 : 매 주 일요일 오전 2시
    @Scheduled(cron = "0 0 2 ? * SUN") // 초 분 시 일 월 요일
    public void run1WRankJob() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);

        try {
            jobLauncher.run(transAction1WReaderJobConfiguration.ranking1WReaderJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 월간 랭킹 배치 : 매 월 1일 오전 3시
    @Scheduled(cron = "0 0 3 1 1/1 ?") // 초 분 시 일 월 요일
    public void run1MRankJob() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);

        try {
            jobLauncher.run(transAction1MReaderJobConfiguration.ranking1MReaderJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
package com.nfteam.server.batch.scheduler;

import com.nfteam.server.batch.job.TransAction12HReaderJobConfiguration;
import com.nfteam.server.batch.job.TransAction24HReaderJobConfiguration;
import com.nfteam.server.batch.job.TransAction6HReaderJobConfiguration;
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
public class TransActionReaderJobScheduler {

    private final JobLauncher jobLauncher;
    private final TransAction6HReaderJobConfiguration transAction6HReaderJobConfiguration;
    private final TransAction12HReaderJobConfiguration transAction12HReaderJobConfiguration;
    private final TransAction24HReaderJobConfiguration transAction24HReaderJobConfiguration;

    @Scheduled(cron = "10 * * * * *") //초 분 시 일 월 요일
    public void run6HJob() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);

        try {
            jobLauncher.run(transAction6HReaderJobConfiguration.ranking6HReaderJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "15 * * * * *") //초 분 시 일 월 요일
    public void run12HJob() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);
        try {
            jobLauncher.run(transAction12HReaderJobConfiguration.ranking12HReaderJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "20 * * * * *") //초 분 시 일 월 요일
    public void run24HJob() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);
        try {
            jobLauncher.run(transAction24HReaderJobConfiguration.ranking24HReaderJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
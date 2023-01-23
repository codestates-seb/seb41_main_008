package com.nfteam.server.batch.scheduler;

import com.nfteam.server.batch.job.*;
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
public class TransActionCoinRankingReaderJobScheduler {

    private final JobLauncher jobLauncher;
    private final TransActionSOLReaderJobConfiguration transActionSOLReaderJobConfiguration;
    private final TransActionBTCReaderJobConfiguration transActionBTCReaderJobConfiguration;
    private final TransActionDOGEReaderJobConfiguration transActionDOGEReaderJobConfiguration;
    private final TransActionETHReaderJobConfiguration transActionETHReaderJobConfiguration;
    private final TransActionETCReaderJobConfiguration transActionETCReaderJobConfiguration;


    // SOL 코인 배치 : 매일 오전 4시
    @Scheduled(cron = "0 0 4 1/1 * ?") // 초 분 시 일 월 요일
    public void runSOLRankJob() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);

        try {
            jobLauncher.run(transActionSOLReaderJobConfiguration.rankingSOLReaderJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // BTC 코인 배치 : 매일 오전 4시 30분
    @Scheduled(cron = "0 30 4 1/1 * ?") // 초 분 시 일 월 요일
    public void runBTCRankJob() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);

        try {
            jobLauncher.run(transActionBTCReaderJobConfiguration.rankingBTCReaderJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DOGE 코인 배치 : 매일 오전 5시
    @Scheduled(cron = "0 0 5 1/1 * ?") // 초 분 시 일 월 요일
    public void runDOGERankJob() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);

        try {
            jobLauncher.run(transActionDOGEReaderJobConfiguration.rankingDOGEReaderJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ETH 코인 배치 : 매일 오전 5시 30분
    @Scheduled(cron = "0 30 5 1/1 * ?") // 초 분 시 일 월 요일
    public void runETHRankJob() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);

        try {
            jobLauncher.run(transActionETHReaderJobConfiguration.rankingETHReaderJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ETC 코인 배치 : 매일 오전 6시
    @Scheduled(cron = "0 0 6 1/1 * ?") // 초 분 시 일 월 요일 연도(op)
    public void runETCRankJob() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);

        try {
            jobLauncher.run(transActionETCReaderJobConfiguration.rankingETCReaderJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
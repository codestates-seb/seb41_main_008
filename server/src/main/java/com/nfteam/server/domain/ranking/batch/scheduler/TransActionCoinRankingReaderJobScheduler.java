package com.nfteam.server.domain.ranking.batch.scheduler;

import com.nfteam.server.domain.ranking.batch.job.*;
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
public class TransActionCoinRankingReaderJobScheduler {

    private final JobLauncher jobLauncher;
    private final RankingService rankingService;

    private final TransActionSOLReaderJobConfiguration transActionSOLReaderJobConfiguration;
    private final TransActionBTCReaderJobConfiguration transActionBTCReaderJobConfiguration;
    private final TransActionDOGEReaderJobConfiguration transActionDOGEReaderJobConfiguration;
    private final TransActionETHReaderJobConfiguration transActionETHReaderJobConfiguration;
    private final TransActionETCReaderJobConfiguration transActionETCReaderJobConfiguration;

    public TransActionCoinRankingReaderJobScheduler(JobLauncher jobLauncher,
                                                    RankingService rankingService,
                                                    TransActionSOLReaderJobConfiguration transActionSOLReaderJobConfiguration,
                                                    TransActionBTCReaderJobConfiguration transActionBTCReaderJobConfiguration,
                                                    TransActionDOGEReaderJobConfiguration transActionDOGEReaderJobConfiguration,
                                                    TransActionETHReaderJobConfiguration transActionETHReaderJobConfiguration,
                                                    TransActionETCReaderJobConfiguration transActionETCReaderJobConfiguration) {
        this.jobLauncher = jobLauncher;
        this.rankingService = rankingService;
        this.transActionSOLReaderJobConfiguration = transActionSOLReaderJobConfiguration;
        this.transActionBTCReaderJobConfiguration = transActionBTCReaderJobConfiguration;
        this.transActionDOGEReaderJobConfiguration = transActionDOGEReaderJobConfiguration;
        this.transActionETHReaderJobConfiguration = transActionETHReaderJobConfiguration;
        this.transActionETCReaderJobConfiguration = transActionETCReaderJobConfiguration;
    }

    // SOL ?????? ?????? : ?????? ?????? 4???
    @Scheduled(cron = "0 0 4 1/1 * ?")
    public void runSOLRankJob() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);

        try {
            jobLauncher.run(transActionSOLReaderJobConfiguration.rankingSOLReaderJob(), jobParameters);
            // ?????? ?????? ?????? ??????
            rankingService.deleteCoinRankingCache(1L);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error("SOL ?????? ?????? ?????? ?????? : {} / ?????? : {}", e.getMessage(), LocalDateTime.now());
        } catch (Exception e) {
            log.error("SOL ?????? ?????? ?????? ?????? : {} / ?????? : {}", e.getMessage(), LocalDateTime.now());
        }
    }

    // BTC ?????? ?????? : ?????? ?????? 4??? 30???
    @Scheduled(cron = "0 30 4 1/1 * ?")
    public void runBTCRankJob() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);

        try {
            jobLauncher.run(transActionBTCReaderJobConfiguration.rankingBTCReaderJob(), jobParameters);
            // ?????? ?????? ?????? ??????
            rankingService.deleteCoinRankingCache(2L);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error("BTC ?????? ?????? ?????? ?????? : {} / ?????? : {}", e.getMessage(), LocalDateTime.now());
        } catch (Exception e) {
            log.error("BTC ?????? ?????? ?????? ?????? : {} / ?????? : {}", e.getMessage(), LocalDateTime.now());
        }
    }

    // DOGE ?????? ?????? : ?????? ?????? 5???
    @Scheduled(cron = "0 0 5 1/1 * ?")
    public void runDOGERankJob() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);

        try {
            jobLauncher.run(transActionDOGEReaderJobConfiguration.rankingDOGEReaderJob(), jobParameters);
            // ?????? ?????? ?????? ??????
            rankingService.deleteCoinRankingCache(3L);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error("DOGE ?????? ?????? ?????? ?????? : {} / ?????? : {}", e.getMessage(), LocalDateTime.now());
        } catch (Exception e) {
            log.error("DOGE ?????? ?????? ?????? ?????? : {} / ?????? : {}", e.getMessage(), LocalDateTime.now());
        }
    }

    // ETH ?????? ?????? : ?????? ?????? 5??? 30???
    @Scheduled(cron = "0 30 5 1/1 * ?")
    public void runETHRankJob() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);

        try {
            jobLauncher.run(transActionETHReaderJobConfiguration.rankingETHReaderJob(), jobParameters);
            // ?????? ?????? ?????? ??????
            rankingService.deleteCoinRankingCache(4L);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error("ETH ?????? ?????? ?????? ?????? : {} / ?????? : {}", e.getMessage(), LocalDateTime.now());
        } catch (Exception e) {
            log.error("ETH ?????? ?????? ?????? ?????? : {} / ?????? : {}", e.getMessage(), LocalDateTime.now());
        }
    }

    // ETC ?????? ?????? : ?????? ?????? 6???
    @Scheduled(cron = "0 0 6 1/1 * ?")
    public void runETCRankJob() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);

        try {
            jobLauncher.run(transActionETCReaderJobConfiguration.rankingETCReaderJob(), jobParameters);
            // ?????? ?????? ?????? ??????
            rankingService.deleteCoinRankingCache(5L);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error("ETC ?????? ?????? ?????? ?????? : {} / ?????? : {}", e.getMessage(), LocalDateTime.now());
        } catch (Exception e) {
            log.error("ETC ?????? ?????? ?????? ?????? : {} / ?????? : {}", e.getMessage(), LocalDateTime.now());
        }
    }

}
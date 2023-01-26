package com.nfteam.server.batch.scheduler;

import com.nfteam.server.batch.job.*;
import com.nfteam.server.ranking.service.RankingService;
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

import java.time.LocalDateTime;
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
    private final RankingService rankingService;

    // SOL 코인 배치 : 매일 오전 4시
    @Scheduled(cron = "0 0 4 1/1 * ?") // 초 분 시 일 월 요일
    public void runSOLRankJob() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);

        try {
            jobLauncher.run(transActionSOLReaderJobConfiguration.rankingSOLReaderJob(), jobParameters);
            // 이전 캐시 정보 삭제
            rankingService.deleteCoinRankingCache(1L);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error("SOL 코인 일간 배치 에러 : {} / 날짜 : {}", e.getMessage(), LocalDateTime.now());
        } catch (Exception e) {
            log.error("SOL 코인 일간 배치 에러 : {} / 날짜 : {}", e.getMessage(), LocalDateTime.now());
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
            // 이전 캐시 정보 삭제
            rankingService.deleteCoinRankingCache(2L);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error("BTC 코인 일간 배치 에러 : {} / 날짜 : {}", e.getMessage(), LocalDateTime.now());
        } catch (Exception e) {
            log.error("BTC 코인 일간 배치 에러 : {} / 날짜 : {}", e.getMessage(), LocalDateTime.now());
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
            // 이전 캐시 정보 삭제
            rankingService.deleteCoinRankingCache(3L);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error("DOGE 코인 일간 배치 에러 : {} / 날짜 : {}", e.getMessage(), LocalDateTime.now());
            ;
        } catch (Exception e) {
            log.error("DOGE 코인 일간 배치 에러 : {} / 날짜 : {}", e.getMessage(), LocalDateTime.now());
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
            // 이전 캐시 정보 삭제
            rankingService.deleteCoinRankingCache(4L);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error("ETH 코인 일간 배치 에러 : {} / 날짜 : {}", e.getMessage(), LocalDateTime.now());
        } catch (Exception e) {
            log.error("ETH 코인 일간 배치 에러 : {} / 날짜 : {}", e.getMessage(), LocalDateTime.now());
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
            // 이전 캐시 정보 삭제
            rankingService.deleteCoinRankingCache(5L);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            log.error("ETC 코인 일간 배치 에러 : {} / 날짜 : {}", e.getMessage(), LocalDateTime.now());
        } catch (Exception e) {
            log.error("ETC 코인 일간 배치 에러 : {} / 날짜 : {}", e.getMessage(), LocalDateTime.now());
        }
    }

}
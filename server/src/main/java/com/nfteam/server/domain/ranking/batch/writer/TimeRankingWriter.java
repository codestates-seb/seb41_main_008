package com.nfteam.server.domain.ranking.batch.writer;

import com.nfteam.server.domain.ranking.batch.entity.TimeRankingEntity;
import com.nfteam.server.domain.ranking.batch.repository.TimeRankingRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimeRankingWriter implements ItemWriter<TimeRankingEntity> {

    private final TimeRankingRepository timeRankingRepository;

    public TimeRankingWriter(TimeRankingRepository timeRankingRepository) {
        this.timeRankingRepository = timeRankingRepository;
    }

    @Override
    public void write(List<? extends TimeRankingEntity> items) throws Exception {
        TimeRankingEntity timeRankingEntity = items.get(0);
        String[] rank = timeRankingEntity.getRankString().split(",");
        if (rank.length > 14) {
            // 거래량이 충분하여 랭킹이 15개 이상 형성된 경우 랭킹 기록을 갱신
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 15; i++) {
                sb.append(rank[i]);
                sb.append("/");
            }
            timeRankingEntity.updateRank(sb.toString());
            timeRankingRepository.save(timeRankingEntity);
        }
    }

}
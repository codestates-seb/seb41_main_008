package com.nfteam.server.batch.writer;

import com.nfteam.server.batch.entity.TimeRankingEntity;
import com.nfteam.server.batch.repository.TimeRankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TimeRankingWriter implements ItemWriter<TimeRankingEntity> {

    private final TimeRankingRepository timeRankingRepository;

    @Override
    public void write(List<? extends TimeRankingEntity> items) throws Exception {
        TimeRankingEntity timeRankingEntity = items.get(0);
        String[] rank = timeRankingEntity.getRankString().split(",");
        if (rank.length > 15) {
            // 거래량이 충분하여 랭킹이 15위까지 형성된 경우 랭킹 기록을 갱신
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 15; i++) {
                sb.append(rank[i]);
                sb.append("/");
            }
            sb.delete(sb.length() - 1, sb.length());
            timeRankingEntity.updateRank(sb.toString());
            timeRankingRepository.save(timeRankingEntity);
        }
    }

}

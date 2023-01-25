package com.nfteam.server.batch.writer;

import com.nfteam.server.batch.entity.CoinRankingEntity;
import com.nfteam.server.batch.repository.CoinRankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CoinRankingWriter implements ItemWriter<CoinRankingEntity> {

    private final CoinRankingRepository coinRankingRepository;

    @Override
    public void write(List<? extends CoinRankingEntity> items) throws Exception {
        CoinRankingEntity coinRankingEntity = items.get(0);
        String[] rank = coinRankingEntity.getRankString().split(",");
        if (rank.length > 14) {
            // 거래량이 충분하여 랭킹이 15위까지 형성된 경우 랭킹 기록을 갱신
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 15; i++) {
                sb.append(rank[i]);
                sb.append("/");
            }
            sb.delete(sb.length() - 1, sb.length());
            coinRankingEntity.updateRank(sb.toString());
            coinRankingRepository.save(coinRankingEntity);
        }
    }

}
package com.nfteam.server.batch.writer;

import com.nfteam.server.batch.entity.Ranking1M;
import com.nfteam.server.batch.repository.Ranking1MRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Ranking1MWriter implements ItemWriter<Ranking1M> {

    private final Ranking1MRepository ranking1MRepository;

    @Override
    public void write(List<? extends Ranking1M> items) throws Exception {
        Ranking1M ranking1M = items.get(0);
        String[] rank = ranking1M.getRankString().split(",");
        if (rank.length > 15) {
            // 거래량이 충분하여 랭킹이 15위까지 형성된 경우 랭킹 기록을 갱신
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 15; i++) {
                sb.append(rank[i]);
                sb.append("/");
            }
            sb.deleteCharAt(sb.lastIndexOf("/"));
            ranking1M.changeString(sb.toString());
            ranking1MRepository.save(ranking1M);
        }
    }

}

package com.nfteam.server.batch.writer;

import com.nfteam.server.batch.entity.Ranking1W;
import com.nfteam.server.batch.repository.Ranking1WRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Ranking1WWriter implements ItemWriter<Ranking1W> {

    private final Ranking1WRepository ranking1WRepository;

    @Override
    public void write(List<? extends Ranking1W> items) throws Exception {
        Ranking1W ranking1W = items.get(0);
        String[] rank = ranking1W.getRankString().split(",");
        if (rank.length > 15) {
            // 거래량이 충분하여 랭킹이 15위까지 형성된 경우 랭킹 기록을 갱신
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 15; i++) {
                sb.append(rank[i]);
                sb.append("/");
            }
            sb.deleteCharAt(sb.lastIndexOf("/"));
            ranking1W.changeString(sb.toString());
            ranking1WRepository.save(ranking1W);
        }
    }

}

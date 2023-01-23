package com.nfteam.server.batch.writer;

import com.nfteam.server.batch.entity.Ranking1D;
import com.nfteam.server.batch.repository.Ranking1DRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Ranking1DWriter implements ItemWriter<Ranking1D> {

    private final Ranking1DRepository ranking1DRepository;

    @Override
    public void write(List<? extends Ranking1D> items) throws Exception {
        Ranking1D ranking1D = items.get(0);
        String[] rank = ranking1D.getRankString().split(",");
        if (rank.length > 15) {
            // 거래량이 충분하여 랭킹이 15위까지 형성된 경우 랭킹 기록을 갱신
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 15; i++) {
                sb.append(rank[i]);
                sb.append("/");
            }
            sb.delete(sb.length() - 1, sb.length());
            ranking1D.changeString(sb.toString());
            ranking1DRepository.save(ranking1D);
        }
    }

}

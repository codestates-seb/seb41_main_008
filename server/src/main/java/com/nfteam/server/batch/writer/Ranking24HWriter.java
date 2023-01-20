package com.nfteam.server.batch.writer;

import com.nfteam.server.batch.entity.Ranking24H;
import com.nfteam.server.batch.repository.Ranking24HRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Ranking24HWriter implements ItemWriter<Ranking24H> {

    private final Ranking24HRepository ranking24HRepository;

    @Override
    public void write(List<? extends Ranking24H> items) throws Exception {
        Ranking24H ranking24H = items.get(0);
        String[] split = ranking24H.getRankString().split(",");
        if (split.length > 15) {
            ranking24HRepository.save(ranking24H);
        }
    }

}

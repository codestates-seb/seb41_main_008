package com.nfteam.server.batch.writer;

import com.nfteam.server.batch.entity.Ranking12H;
import com.nfteam.server.batch.repository.Ranking12HRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Ranking12HWriter implements ItemWriter<Ranking12H> {

    private final Ranking12HRepository ranking12HRepository;

    @Override
    public void write(List<? extends Ranking12H> items) throws Exception {
        Ranking12H ranking12H = items.get(0);
        String[] split = ranking12H.getRankString().split(",");
        if (split.length > 15) {
            ranking12HRepository.save(ranking12H);
        }
    }

}

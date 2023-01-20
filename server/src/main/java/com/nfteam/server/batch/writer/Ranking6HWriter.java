package com.nfteam.server.batch.writer;

import com.nfteam.server.batch.entity.Ranking6H;
import com.nfteam.server.batch.repository.Ranking6HRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Ranking6HWriter implements ItemWriter<Ranking6H> {

    private final Ranking6HRepository ranking6HRepository;

    @Override
    public void write(List<? extends Ranking6H> items) throws Exception {
        Ranking6H ranking6H = items.get(0);
        String[] split = ranking6H.getRankString().split(",");
        if (split.length > 15) {
            ranking6HRepository.save(ranking6H);
        }
    }

}

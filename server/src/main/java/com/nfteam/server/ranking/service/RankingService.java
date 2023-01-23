package com.nfteam.server.ranking.service;

import com.nfteam.server.dto.response.ranking.RankingResponse;
import com.nfteam.server.exception.ranking.RankCriteriaNotValidException;
import com.nfteam.server.ranking.repository.QRankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankingService {

    private final QRankingRepository qRankingRepository;

    public List<RankingResponse> getTimeRanking(String time) {
        Integer timeCriteria = getCriteria(time);

        // 해당 구간 랭킹 아이디 리스트 불러오기
        String rankString = qRankingRepository.getRankString(timeCriteria);

        String[] split = rankString.split("/");

        // 불러온 아이디별로 collection 조회 후 세팅하기
        return null;
    }

    private Integer getCriteria(String time) {
        switch (time) {
            case "day":
                return 24;
            case "week":
                return 168;
            case "month":
                return 720;
            default:
                throw new RankCriteriaNotValidException();
        }
    }

}

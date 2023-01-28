package com.nfteam.server.domain.ranking.service;

import com.nfteam.server.domain.item.entity.Item;
import com.nfteam.server.domain.item.repository.ItemRepository;
import com.nfteam.server.domain.ranking.repository.QRankingRepository;
import com.nfteam.server.dto.response.ranking.RankingResponse;
import com.nfteam.server.exception.item.ItemCollectionNotFoundException;
import com.nfteam.server.exception.ranking.RankCoinCriteriaNotValidException;
import com.nfteam.server.exception.ranking.RankTimeCriteriaNotValidException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RankingService {

    private final QRankingRepository qRankingRepository;
    private final ItemRepository itemRepository;

    public RankingService(QRankingRepository qRankingRepository, ItemRepository itemRepository) {
        this.qRankingRepository = qRankingRepository;
        this.itemRepository = itemRepository;
    }

    @Cacheable("timeRanking")
    public List<RankingResponse> getTimeRanking(String time) {
        checkTimeValidate(time);
        String[] ranking = qRankingRepository.getTimeRankString(time).split("/");
        return getRankingResponses(ranking);
    }

    @Cacheable("coinRanking")
    public List<RankingResponse> getCoinRanking(Long coinId) {
        checkCoinIdValidate(coinId);
        String[] ranking = qRankingRepository.getCoinRankString(coinId).split("/");
        return getRankingResponses(ranking);
    }

    // 캐시 삭제 전용 메서드
    @CacheEvict("timeRanking")
    public void deleteTimeRankingCache(String time) {
    }

    @CacheEvict("coinRanking")
    public void deleteCoinRankingCache(Long coinId) {
    }

    private void checkTimeValidate(String time) {
        switch (time) {
            case "day":
            case "week":
            case "month":
                break;
            default:
                throw new RankTimeCriteriaNotValidException();
        }
    }

    private void checkCoinIdValidate(Long coinId) {
        if (coinId < 1 || coinId > 5) throw new RankCoinCriteriaNotValidException();
    }

    private List<RankingResponse> getRankingResponses(String[] ranking) {
        List<RankingResponse> rankingResponses = new ArrayList<>();

        for (int i = 0; i < ranking.length; i++) {
            Long collectionId = Long.parseLong(ranking[i]);
            RankingResponse rankingResponse = qRankingRepository.findRankingCollectionInfo(collectionId);

            // 컬렉션 값이 없을 경우 에러처리
            if (rankingResponse == null) throw new ItemCollectionNotFoundException(collectionId);

            // 랭킹 세팅
            rankingResponse.addRanking(i + 1);

            // 아이템 메타정보 세팅
            List<Item> items = itemRepository.findItemsByCollectionId(collectionId);
            if (!items.isEmpty()) {
                Double totalVolume = items.stream().mapToDouble(item -> item.getItemPrice()).sum();
                Double highestPrice = items.stream().mapToDouble(item -> item.getItemPrice()).max().getAsDouble();
                rankingResponse.addMetaInfo(totalVolume, highestPrice);
            } else {
                rankingResponse.addMetaInfo(0.0, 0.0);
            }

            rankingResponses.add(rankingResponse);
        }

        return rankingResponses;
    }

}
package com.nfteam.server.domain.ranking.service;

import com.nfteam.server.domain.item.entity.Item;
import com.nfteam.server.domain.item.repository.ItemRepository;
import com.nfteam.server.domain.ranking.repository.QRankingRepository;
import com.nfteam.server.dto.response.ranking.RankingResponse;
import com.nfteam.server.exception.ranking.RankCoinCriteriaNotValidException;
import com.nfteam.server.exception.ranking.RankTimeCriteriaNotValidException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    //@Cacheable("timeRanking")
    public List<RankingResponse> getTimeRanking(String time) {
        LocalDate localDate = checkTimeValidate(time);
        List<Long> timeRankCollectionIdList = qRankingRepository.getTimeRankCollectionId(localDate);
        return getRankingResponses(timeRankCollectionIdList);
    }

    //@Cacheable("coinRanking")
    public List<RankingResponse> getCoinRanking(Long coinId) {
        checkCoinIdValidate(coinId);
        List<Long> coinRankCollectionId = qRankingRepository.getCoinRankCollectionId(coinId);
        return getRankingResponses(coinRankCollectionId);
    }

    // 캐시 삭제 전용 메서드
    @CacheEvict("timeRanking")
    public void deleteTimeRankingCache(String time) {
    }

    @CacheEvict("coinRanking")
    public void deleteCoinRankingCache(Long coinId) {
    }

    private LocalDate checkTimeValidate(String time) {
        switch (time) {
            case "day":
                return LocalDate.now().minusDays(1);
            case "week":
                return LocalDate.now().minusWeeks(1);
            case "month":
                return LocalDate.now().minusMonths(1);
            default:
                throw new RankTimeCriteriaNotValidException();
        }
    }

    private void checkCoinIdValidate(Long coinId) {
        if (coinId < 1 || coinId > 5) throw new RankCoinCriteriaNotValidException();
    }

    private List<RankingResponse> getRankingResponses(List<Long> collectionIdList) {
        List<RankingResponse> rankingResponses = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            RankingResponse rankingResponse = qRankingRepository.findRankingCollectionInfo(collectionIdList.get(i));

            // 랭킹 세팅
            rankingResponse.addRanking(i + 1);

            // 아이템 메타정보 세팅
            List<Item> items = itemRepository.findItemsByCollectionId(rankingResponse.getCollectionId());

            if (!items.isEmpty()) {
                Double totalVolume = items.stream().mapToDouble(item -> item.getItemPrice()).sum();
                Double highestPrice = items.stream().mapToDouble(item -> item.getItemPrice()).max().getAsDouble();
                rankingResponse.addMetaInfo(totalVolume, highestPrice);
            } else {
                rankingResponse.addMetaInfo(0.0, 0.0);
            }
        }

        return rankingResponses;
    }

}
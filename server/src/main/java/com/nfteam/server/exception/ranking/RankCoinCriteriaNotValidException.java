package com.nfteam.server.exception.ranking;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class RankCoinCriteriaNotValidException extends NFTCustomException {

    private static final String message = "랭킹 조회 기준값이 올바르지 않습니다. - coinId 1 - 5번 중 선택";

    public RankCoinCriteriaNotValidException() {
        super(ExceptionCode.RANK_NOT_FOUND, message);
    }

}
package com.nfteam.server.exception.ranking;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class RankTimeCriteriaNotValidException extends NFTCustomException {

    private static final String message = "랭킹 조회 기준값이 올바르지 않습니다. - day/week/month 중 선택";

    public RankTimeCriteriaNotValidException() {
        super(ExceptionCode.RANK_NOT_FOUND, message);
    }

}

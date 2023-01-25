package com.nfteam.server.exception.coinRel;

import com.nfteam.server.exception.ExceptionCode;

public class CoinMemberRelNotFoundException extends CoinCustomException {

    private static final String ErrorMessage = "해당 멤버는 존재하지 않습니다.";

    public CoinMemberRelNotFoundException(Long memberid) {
        super(ExceptionCode.MEMBER_NOT_FOUND, String.format("%s- 회원 아이디: % ㅇ", ErrorMessage, memberid));
    }

    public CoinMemberRelNotFoundException(String memberEmail, String coinName) {
        super(ExceptionCode.COIN_MEMBER_REL_NOT_FOUND, String.format("%s -회원 이메일: % ㅇ,코인 이름: % ㅇ", ErrorMessage, memberEmail, coinName));
    }

}
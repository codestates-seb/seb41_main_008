package com.nfteam.server.exception.Coin;

import com.nfteam.server.exception.ExceptionCode;

public class CoinMemberRelNotFoundException extends CoinCustomException {

    private static final String ErrorMessage="해당 멤버는 존재하지 않습니다.";

    public CoinMemberRelNotFoundException(Long memberid){
        super(ExceptionCode.MEMBER_NOT_FOUND, String.format("%s- 회원 아이디: % ㅇ",ErrorMessage,memberid));
    }

}

package com.nfteam.server.exception.member;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class MemberStatusNotActiveException extends NFTCustomException {

    private static final String message = "탈퇴한 회원입니다.";

    public MemberStatusNotActiveException() {
        super(ExceptionCode.MEMBER_NOT_ACTIVE, message);
    }

}
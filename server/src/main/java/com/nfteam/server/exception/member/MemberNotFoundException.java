package com.nfteam.server.exception.member;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class MemberNotFoundException extends NFTCustomException {

    private static final String message = "해당 회원 정보가 존재하지 않습니다.";

    // 아이디로 회원을 찾았을 때 회원이 없을 경우
    public MemberNotFoundException(Long memberId) {
        super(ExceptionCode.MEMBER_NOT_FOUND, String.format("%s - 회원 아이디 : %d", message, memberId));
    }

    // 이메일로 회원을 찾았을 때 회원이 없을 경우
    public MemberNotFoundException(String email) {
        super(ExceptionCode.MEMBER_NOT_FOUND, String.format("%s - 회원 이메일 : %s", message, email));
    }

}
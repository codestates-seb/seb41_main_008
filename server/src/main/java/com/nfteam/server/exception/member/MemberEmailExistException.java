package com.nfteam.server.exception.member;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class MemberEmailExistException extends NFTCustomException {

    private static final String message = "해당 이메일을 가진 회원이 이미 존재합니다.";

    public MemberEmailExistException(String email) {
        super(ExceptionCode.MEMBER_EMAIL_EXIST, String.format("%s - 중복된 이메일 : %s", message, email));
    }

}
package com.nfteam.server.exception.item;

import com.nfteam.server.exception.ExceptionCode;
import com.nfteam.server.exception.NFTCustomException;

public class ItemCollectionNotFoundException extends NFTCustomException {

    private static final String message = "해당 컬렉션 정보가 존재하지 않습니다.";

    public ItemCollectionNotFoundException(Long collectionId) {
        super(ExceptionCode.ITEM_COLLECTION_NOT_FOUND, String.format("%s - 컬렉션 아이디 : %d", message, collectionId));
    }

}

package com.nfteam.server.dto.response;

import lombok.Getter;

@Getter
public class ImageResponse {
    private String imageUrl;

    public ImageResponse(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

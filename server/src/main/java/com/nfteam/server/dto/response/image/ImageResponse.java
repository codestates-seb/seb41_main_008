package com.nfteam.server.dto.response.image;

import lombok.Getter;

@Getter
public class ImageResponse {
    private String imageUrl;

    public ImageResponse(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

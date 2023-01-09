package com.nfteam.server.image.controller;

import com.nfteam.server.dto.response.image.ImageResponse;
import com.nfteam.server.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping(
            path = "/items/{itemId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity<ImageResponse> upload(@PathVariable("itemId") Long itemId,
                                                @RequestPart MultipartFile multipartFile) throws IOException {
        ImageResponse imageResponse = imageService.uploadItemImg(itemId, multipartFile, "items");
        return ResponseEntity.ok(imageResponse);
    }


}

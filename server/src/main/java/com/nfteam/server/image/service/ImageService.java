package com.nfteam.server.image.service;

import com.nfteam.server.dto.response.ImageResponse;
import com.nfteam.server.exception.item.ItemNotFoundException;
import com.nfteam.server.item.entity.Item;
import com.nfteam.server.item.repository.ItemRepository;
import com.nfteam.server.support.S3ImageUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {
    private final S3ImageUploader s3ImageUploader;
    private final ItemRepository itemRepository;


    @Transactional
    public ImageResponse uploadItemImg(Long itemId, MultipartFile multipartFile, String dir) throws IOException {
        String imageUrl = s3ImageUploader.uploadImage(dir, multipartFile);
//        Item item = itemRepository.findById(itemId)
//                .orElseThrow(() -> new ItemNotFoundException(itemId));
//        item.addImgUrl(imageUrl);

        return new ImageResponse(imageUrl);
    }
}

package com.nfteam.server.fileupload.service;

import com.nfteam.server.dto.response.file.FileResponse;
import com.nfteam.server.fileupload.utils.S3ImageUploader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
public class FileService {

    private final S3ImageUploader s3ImageUploader;

    public FileService(S3ImageUploader s3ImageUploader) {
        this.s3ImageUploader = s3ImageUploader;
    }

    @Transactional
    public FileResponse uploadFile(MultipartFile file) throws IOException {
        return s3ImageUploader.uploadImage(file, getExtension(file));
    }

    private String getExtension(MultipartFile multipartFile) {
        return multipartFile.getContentType().split("/")[1];
    }

}
package com.nfteam.server.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.nfteam.server.dto.response.file.FileResponse;
import com.nfteam.server.exception.support.ImageConvertingFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ImageUploader {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public FileResponse uploadImage(MultipartFile file, String extension) throws IOException {
        File convertedFile = convertFile(file).orElseThrow(ImageConvertingFailedException::new);

        String fileName = UUID.randomUUID() + "." + extension;
        String fileUrl = pushToS3(convertedFile, fileName);

        deleteLocalFile(convertedFile);

        return new FileResponse(fileUrl, fileName);
    }

    private Optional<File> convertFile(MultipartFile multipartFile) throws IOException {
        File convertedFile = new File(System.getProperty("java.io.tmpdir")
                + System.getProperty("file.separator") + multipartFile.getOriginalFilename());

        if (convertedFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(convertedFile);
        }

        return Optional.empty();
    }

    private String pushToS3(File file, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead)    // PublicRead 권한으로 업로드
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void deleteLocalFile(File file) {
        if (!file.delete()) {
            log.warn("로컬 파일 삭제 실패");
        }
    }
}

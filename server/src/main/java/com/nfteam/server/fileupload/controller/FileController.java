package com.nfteam.server.fileupload.controller;

import com.nfteam.server.dto.response.file.FileResponse;
import com.nfteam.server.fileupload.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class FileController {

    private final FileService fileService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FileResponse> uploadFile(@RequestPart MultipartFile file) throws IOException {
        return ResponseEntity.ok(fileService.uploadFile(file));
    }

}
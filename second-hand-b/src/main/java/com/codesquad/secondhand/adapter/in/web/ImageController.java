package com.codesquad.secondhand.adapter.in.web;

import com.codesquad.secondhand.application.port.ImageUseCase;
import com.codesquad.secondhand.application.port.in.response.ImageUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/images")
@RestController
public class ImageController {

    private final ImageUseCase imageUse;

    @PostMapping
    public ResponseEntity<ImageUploadResponse> upload(@RequestParam("file") MultipartFile file) {
        ImageUploadResponse imageUploadResponse = imageUse.upload(file);
        return ResponseEntity.ok()
                .body(imageUploadResponse);
    }
}

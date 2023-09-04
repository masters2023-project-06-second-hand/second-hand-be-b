package com.codesquad.secondhand.adapter.in.web;

import com.codesquad.secondhand.application.port.in.ImageUseCase;
import com.codesquad.secondhand.application.port.in.response.ImageUploadResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/images")
@RestController
public class ImageController {

    private final ImageUseCase imageUseCase;

    @PostMapping
    public ResponseEntity<ImageUploadResponse> upload(@RequestParam("file") MultipartFile file) {
        ImageUploadResponse imageUploadResponse = imageUseCase.upload(file);
        return ResponseEntity.ok()
                .body(imageUploadResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody Map<String, Long> request) {
        imageUseCase.delete(request.get("id"));
        return ResponseEntity.noContent().build();
    }
}

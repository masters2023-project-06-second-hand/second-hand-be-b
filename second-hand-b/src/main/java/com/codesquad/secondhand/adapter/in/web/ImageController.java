package com.codesquad.secondhand.adapter.in.web;

import com.codesquad.secondhand.application.port.in.ImageUseCase;
import com.codesquad.secondhand.application.port.in.request.DeleteImageRequest;
import com.codesquad.secondhand.application.port.in.response.ImageInfo;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageUseCase imageUseCase;

    @PostMapping("/api/products/images")
    public ResponseEntity<ImageInfo> uploadForProduct(@RequestParam MultipartFile file) {
        ImageInfo imageInfo = imageUseCase.uploadWithServer(file);
        return ResponseEntity.ok()
                .body(imageInfo);
    }

    @PostMapping("/api/members/images")
    public ResponseEntity<Map<String, String>> uploadForMember(@RequestParam MultipartFile file) {
        String imgUrl = imageUseCase.uploadOnlyCloud(file);
        return ResponseEntity.ok()
                .body(Map.of("imgUrl", imgUrl));
    }

    @DeleteMapping("/api/images")
    public ResponseEntity<Void> delete(@RequestBody DeleteImageRequest deleteImageRequest) {
        imageUseCase.delete(deleteImageRequest);
        return ResponseEntity.noContent().build();
    }
}

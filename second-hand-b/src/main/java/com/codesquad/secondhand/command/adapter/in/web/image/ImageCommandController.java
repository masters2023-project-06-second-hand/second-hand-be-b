package com.codesquad.secondhand.command.adapter.in.web.image;

import com.codesquad.secondhand.command.adapter.in.web.image.request.DeleteImageRequest;
import com.codesquad.secondhand.command.port.in.FileUseCase;
import com.codesquad.secondhand.command.port.in.ImageUseCase;
import com.codesquad.secondhand.command.adapter.in.web.image.response.ImageInfo;
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
public class ImageCommandController {

    private final FileUseCase fileUseCase;
    private final ImageUseCase imageUseCase;

    @PostMapping("/api/products/images")
    public ResponseEntity<ImageInfo> uploadForProduct(@RequestParam MultipartFile file) {
        String imgUrl = fileUseCase.uploadCloud(file);
        ImageInfo imageInfo = imageUseCase.save(imgUrl);
        return ResponseEntity.ok()
                .body(imageInfo);
    }

    @PostMapping("/api/members/images")
    public ResponseEntity<Map<String, String>> uploadForMember(@RequestParam MultipartFile file) {
        String imgUrl = fileUseCase.uploadCloud(file);
        return ResponseEntity.ok()
                .body(Map.of("imgUrl", imgUrl));
    }

    @DeleteMapping("/api/images")
    public ResponseEntity<Void> delete(@RequestBody DeleteImageRequest deleteImageRequest) {
        imageUseCase.delete(deleteImageRequest);
        return ResponseEntity.noContent().build();
    }
}

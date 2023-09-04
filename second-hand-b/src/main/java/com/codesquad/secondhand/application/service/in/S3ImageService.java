package com.codesquad.secondhand.application.service.in;

import com.codesquad.secondhand.adapter.out.s3.S3StorageService;
import com.codesquad.secondhand.application.port.in.ImageUseCase;
import com.codesquad.secondhand.application.port.in.response.ImageUploadResponse;
import com.codesquad.secondhand.application.port.out.ImageRepository;
import com.codesquad.secondhand.domain.image.Image;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class S3ImageService implements ImageUseCase {

    private final S3StorageService s3StorageService;
    private final ImageRepository imageRepository;

    @Transactional
    @Override
    public ImageUploadResponse upload(MultipartFile file) {
        String imgUrl = s3StorageService.upload(file);
        Image savedImage = imageRepository.save(new Image(imgUrl));
        return new ImageUploadResponse(savedImage.getId(), savedImage.getUrl());
    }

    @Override
    public void delete(Long id) {
        // TODO: Custom Exception 처리
        Image image = imageRepository.findById(id)
                .orElseThrow();
        imageRepository.deleteById(id);
        s3StorageService.delete(image.getUrl());
    }
}

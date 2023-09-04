package com.codesquad.secondhand.application.service.in;

import com.codesquad.secondhand.application.port.in.ImageUseCase;
import com.codesquad.secondhand.application.port.in.response.ImageUploadResponse;
import com.codesquad.secondhand.application.port.out.CloudStorageService;
import com.codesquad.secondhand.application.port.out.ImageRepository;
import com.codesquad.secondhand.domain.image.Image;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ImageService implements ImageUseCase {

    private final CloudStorageService cloudStorageService;
    private final ImageRepository imageRepository;

    @Transactional
    @Override
    public ImageUploadResponse upload(MultipartFile file) {
        String imgUrl = cloudStorageService.upload(file);
        Image savedImage = imageRepository.save(new Image(imgUrl));
        return new ImageUploadResponse(savedImage.getId(), savedImage.getUrl());
    }

    @Override
    public void delete(Long id) {
        // TODO: Custom Exception 처리
        Image image = imageRepository.findById(id)
                .orElseThrow();
        imageRepository.deleteById(id);
        cloudStorageService.delete(image.getUrl());
    }
}

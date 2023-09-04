package com.codesquad.secondhand.application.service.in;

import com.codesquad.secondhand.adapter.out.s3.S3ImageUploader;
import com.codesquad.secondhand.application.port.ImageUseCase;
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

    private final S3ImageUploader s3ImageUploader;
    private final ImageRepository imageRepository;

    @Transactional
    @Override
    public ImageUploadResponse upload(MultipartFile file) {
        String imgUrl = s3ImageUploader.upload(file);
        Image savedImage = imageRepository.save(new Image(imgUrl));
        return new ImageUploadResponse(savedImage.getId(), savedImage.getUrl());
    }
}

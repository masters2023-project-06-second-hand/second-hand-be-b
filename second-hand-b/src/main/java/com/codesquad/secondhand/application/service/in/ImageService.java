package com.codesquad.secondhand.application.service.in;

import com.codesquad.secondhand.application.port.in.ImageUseCase;
import com.codesquad.secondhand.application.port.in.exception.ImageNotFoundException;
import com.codesquad.secondhand.application.port.in.request.DeleteImageRequest;
import com.codesquad.secondhand.application.port.in.response.ImageInfo;
import com.codesquad.secondhand.application.port.out.CloudStorageService;
import com.codesquad.secondhand.application.port.out.ImageRepository;
import com.codesquad.secondhand.domain.image.Image;
import java.util.List;
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
    public ImageInfo upload(MultipartFile file) {
        String imgUrl = cloudStorageService.upload(file);
        Image savedImage = imageRepository.save(new Image(imgUrl));
        return new ImageInfo(savedImage.getId(), savedImage.getUrl());
    }

    @Transactional
    @Override
    public void delete(DeleteImageRequest deleteImageRequest) {
        Long id = deleteImageRequest.getId();
        Image image = getById(id);
        imageRepository.deleteById(image.getId());
    }

    public Image getById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ImageNotFoundException();
                });
    }

    public List<Image> getImageListById(List<Long> ids) {
        List<Image> images = imageRepository.findAllById(ids);
        if (images.size() != ids.size()) {
            throw new ImageNotFoundException();
        }
        return images;
    }
}

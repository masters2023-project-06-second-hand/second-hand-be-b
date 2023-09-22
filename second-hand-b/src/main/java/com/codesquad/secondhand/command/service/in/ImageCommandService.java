package com.codesquad.secondhand.command.service.in;

import com.codesquad.secondhand.command.adapter.in.web.image.request.DeleteImageRequest;
import com.codesquad.secondhand.command.adapter.in.web.image.response.ImageInfo;
import com.codesquad.secondhand.command.port.in.ImageUseCase;
import com.codesquad.secondhand.command.port.out.ImageRepository;
import com.codesquad.secondhand.common.exception.ImageNotFoundException;
import com.codesquad.secondhand.command.domain.product.Image;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ImageCommandService implements ImageUseCase {

    private final ImageRepository imageRepository;

    @Override
    public ImageInfo save(String imgUrl) {
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

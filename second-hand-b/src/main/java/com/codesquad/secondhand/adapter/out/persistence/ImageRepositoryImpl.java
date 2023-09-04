package com.codesquad.secondhand.adapter.out.persistence;

import com.codesquad.secondhand.application.port.out.ImageRepository;
import com.codesquad.secondhand.domain.image.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepository {

    private final ImageJpaRepository imageJpaRepository;

    @Override
    public Image save(Image image) {
        return imageJpaRepository.save(image);
    }
}

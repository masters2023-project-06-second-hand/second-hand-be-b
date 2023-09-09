package com.codesquad.secondhand.adapter.out.persistence;

import com.codesquad.secondhand.adapter.out.persistence.imports.ImageJpaRepository;
import com.codesquad.secondhand.application.port.out.ImageRepository;
import com.codesquad.secondhand.domain.image.Image;
import java.util.List;
import java.util.Optional;
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

    @Override
    public Optional<Image> findById(Long id) {
        return imageJpaRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        imageJpaRepository.deleteById(id);
    }

    @Override
    public List<Image> findAllById(List<Long> ids) {
        return imageJpaRepository.findAllById(ids);
    }
}

package com.codesquad.secondhand.command.port.out;

import com.codesquad.secondhand.command.domain.product.Image;
import java.util.List;
import java.util.Optional;

public interface ImageRepository {

    Image save(Image image);

    Optional<Image> findById(Long id);

    void deleteById(Long id);

    List<Image> findAllById(List<Long> ids);
}

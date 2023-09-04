package com.codesquad.secondhand.adapter.out.persistence;

import com.codesquad.secondhand.domain.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageJpaRepository extends JpaRepository<Image, Long> {

}

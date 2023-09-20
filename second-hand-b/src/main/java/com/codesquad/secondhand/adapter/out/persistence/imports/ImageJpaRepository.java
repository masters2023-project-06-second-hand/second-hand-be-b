package com.codesquad.secondhand.adapter.out.persistence.imports;

import com.codesquad.secondhand.domain.product.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageJpaRepository extends JpaRepository<Image, Long> {

}

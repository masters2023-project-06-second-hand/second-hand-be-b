package com.codesquad.secondhand.command.adapter.out.persistence.imports;

import com.codesquad.secondhand.command.domain.product.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageJpaRepository extends JpaRepository<Image, Long> {

}

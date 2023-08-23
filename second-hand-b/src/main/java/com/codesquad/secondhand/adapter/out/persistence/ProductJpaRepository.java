package com.codesquad.secondhand.adapter.out.persistence;

import com.codesquad.secondhand.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
    
}

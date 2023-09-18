package com.codesquad.secondhand.application.service.in;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.codesquad.secondhand.application.port.out.ProductRepository;
import com.codesquad.secondhand.application.service.in.exception.InvalidEntityStateException;
import com.codesquad.secondhand.application.service.in.exception.ProductNotFoundException;
import com.codesquad.secondhand.application.service.in.prodcut.ProductService;
import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.ProductTestUtils;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductService productService;
    @Mock
    ProductRepository productRepository;

    @DisplayName("아이디로 Product를 조회시 DB에 존재하지 않으면 예외를 던진다")
    @Test
    void getById() {
        // given
        long notExistsId = 1;
        given(productRepository.findById(notExistsId)).willReturn(Optional.empty());

        // when,then
        assertThatThrownBy(() -> productService.getById(notExistsId))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("Product를 저장 실패로 인한 ID 부여 봤지 못하면 예외를 던진다")
    @Test
    void save() {
        // given
        Product product = ProductTestUtils.createTestProduct();
        given(productRepository.save(any())).willReturn(product);

        // when
        assertThatThrownBy(
                () -> productService.save(product))
                // then
                .isInstanceOf(InvalidEntityStateException.class);
    }
}

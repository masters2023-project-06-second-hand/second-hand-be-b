package com.codesquad.secondhand.application.service.in;


import com.codesquad.secondhand.application.port.in.ProductUseCase;
import com.codesquad.secondhand.application.port.in.request.ProductCreateRequest;
import com.codesquad.secondhand.application.port.out.ProductRepository;
import com.codesquad.secondhand.domain.product.Product;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {

    private final ProductRepository productRepository;

    @Transactional
    public Long save(ProductCreateRequest productCreateRequest, String email) {
        return productRepository.save(new Product(productCreateRequest.getName(), productCreateRequest.getContent(),
                productCreateRequest.getPrice(), null, null, null, null)).getId();
    }
}

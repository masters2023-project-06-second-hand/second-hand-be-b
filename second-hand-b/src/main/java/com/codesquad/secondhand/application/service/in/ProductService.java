package com.codesquad.secondhand.application.service.in;


import com.codesquad.secondhand.application.port.in.ProductUseCase;
import com.codesquad.secondhand.application.port.in.request.ProductCreateRequest;
import com.codesquad.secondhand.application.port.in.request.ProductModifyRequest;
import com.codesquad.secondhand.application.port.in.response.ProductDetail;
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
    @Override
    public Long save(ProductCreateRequest productCreateRequest, String email) {
        return productRepository.save(new Product(productCreateRequest.getName(), productCreateRequest.getContent(),
                productCreateRequest.getPrice(), null, null, null, null)).getId();
    }

    @Override
    public ProductDetail getDetails(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        return new ProductDetail(product.getId(), null, product.getName(), null, null, null,
                product.getContent(), product.getPrice(), null, null);
    }

    @Transactional
    @Override
    public void modify(Long id, ProductModifyRequest request) {
        Product product = productRepository.findById(id).orElseThrow();
        product.modifyProduct(request.getName(), request.getContent(), request.getPrice(), null, null, null);
    }
}

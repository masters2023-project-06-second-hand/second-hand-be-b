package com.codesquad.secondhand.application.service.in.prodcut;

import static com.codesquad.secondhand.application.service.in.prodcut.ProductMapper.toProductDetail;
import static com.codesquad.secondhand.application.service.in.prodcut.ProductMapper.toProductsInfo;

import com.codesquad.secondhand.application.port.in.ProductUseCase;
import com.codesquad.secondhand.application.service.in.exception.InvalidEntityStateException;
import com.codesquad.secondhand.application.service.in.exception.ProductNotFoundException;
import com.codesquad.secondhand.adapter.in.web.request.ProductCreateRequest;
import com.codesquad.secondhand.adapter.in.web.request.ProductModifyRequest;
import com.codesquad.secondhand.adapter.in.web.response.ProductDetail;
import com.codesquad.secondhand.adapter.in.web.response.ProductInfo;
import com.codesquad.secondhand.application.port.out.ProductRepository;
import com.codesquad.secondhand.application.service.in.CategoryService;
import com.codesquad.secondhand.application.service.in.ImageService;
import com.codesquad.secondhand.application.service.in.RegionService;
import com.codesquad.secondhand.domain.image.Image;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.product.Category;
import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import com.codesquad.secondhand.domain.region.Region;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {

    private final ProductRepository productRepository;
    private final RegionService regionService;
    private final CategoryService categoryService;
    private final ImageService imageService;

    @Transactional
    @Override
    public long save(ProductCreateRequest productCreateRequest, Member member) {
        Product product = toProduct(productCreateRequest, member);
        Long id = productRepository.save(product).getId();
        if (id == null) {
            throw new InvalidEntityStateException();
        }
        return id;
    }

    @Override
    public ProductDetail getDetails(long id) {
        Product product = getById(id);
        return toProductDetail(product);
    }

    @Transactional
    @Override
    public void modify(long id, ProductModifyRequest productModifyRequest) {
        Product product = getById(id);
        modifyProduct(productModifyRequest, product);
    }

    @Transactional
    @Override
    public void modifyStatus(long id, String status) {
        Product product = getById(id);
        product.modifyStatus(status);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductInfo> getProductsByRegion(long regionId) {
        List<Product> products = productRepository.findByRegionId(regionId);
        return toProductsInfo(products);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductInfo> getProductsByRegionAndCategory(long regionId, long categoryId) {
        List<Product> products = productRepository.findByRegionIdAndCategoryId(regionId, categoryId);
        return toProductsInfo(products);
    }

    @Transactional
    @Override
    public void delete(long id) {
        productRepository.deleteById(id);
    }

    public Product getById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    public List<ProductInfo> getProductsByMemberId(long memberId) {
        List<Product> products = productRepository.findProductsByMemberId(memberId);
        return toProductsInfo(products);
    }

    public List<ProductInfo> getProductsByMemberIdAndCategoryId(long memberId, long categoryId) {
        List<Product> products = productRepository.findProductsByMemberIdAndCategoryId(
                memberId,
                categoryId
        );
        return toProductsInfo(products);
    }

    public List<ProductInfo> getByWriterId(long memberId) {
        List<Product> products = productRepository.findByWriterId(memberId);
        return toProductsInfo(products);
    }

    public List<ProductInfo> getSoldOutByWriterId(long memberId) {
        List<Product> products = productRepository.findByWriterIdAndStatus(memberId, Status.SOLD_OUT);
        return toProductsInfo(products);
    }

    public List<ProductInfo> getSalesByWriterId(long memberId) {
        List<Product> products = productRepository.findByWriterIdAndStatusNot(memberId, Status.SOLD_OUT);
        return toProductsInfo(products);
    }


    private Product toProduct(ProductCreateRequest productCreateRequest, Member member) {
        Region region = regionService.getById(productCreateRequest.getRegionId());
        Category category = categoryService.getById(productCreateRequest.getCategoryId());

        List<Long> imagesId = productCreateRequest.getImagesId();
        List<Image> images = imageService.getImageListById(imagesId);
        String thumbnailUrl = ProductUnits.getThumbnailUrl(images);

        return new Product(productCreateRequest.getName(),
                productCreateRequest.getContent(),
                productCreateRequest.getPrice(),
                member,
                category,
                thumbnailUrl,
                images,
                region,
                Status.ON_SALES,
                LocalDateTime.now());
    }

    private void modifyProduct(ProductModifyRequest productModifyRequest, Product product) {
        Category category = categoryService.getById(productModifyRequest.getCategoryId());

        List<Long> imagesId = productModifyRequest.getImagesId();
        List<Image> images = imageService.getImageListById(imagesId);
        String thumbnailUrl = ProductUnits.getThumbnailUrl(images);

        Region region = regionService.getById(productModifyRequest.getRegionId());
        product.modifyProduct(
                productModifyRequest.getName(),
                productModifyRequest.getContent(),
                productModifyRequest.getPrice(),
                category,
                thumbnailUrl,
                images,
                region);
    }
}

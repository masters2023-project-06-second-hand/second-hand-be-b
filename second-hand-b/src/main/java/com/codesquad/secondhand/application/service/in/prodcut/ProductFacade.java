package com.codesquad.secondhand.application.service.in.prodcut;

import static com.codesquad.secondhand.application.service.in.prodcut.ProductMapper.toProductDetail;
import static com.codesquad.secondhand.application.service.in.prodcut.ProductMapper.toProductInfo;

import com.codesquad.secondhand.adapter.in.web.request.ProductCreateRequest;
import com.codesquad.secondhand.adapter.in.web.request.ProductModifyRequest;
import com.codesquad.secondhand.adapter.in.web.response.ProductDetail;
import com.codesquad.secondhand.adapter.in.web.response.ProductInfo;
import com.codesquad.secondhand.application.port.in.ProductUseCase;
import com.codesquad.secondhand.application.service.in.CategoryService;
import com.codesquad.secondhand.application.service.in.MemberService;
import com.codesquad.secondhand.application.service.in.image.ImageService;
import com.codesquad.secondhand.application.service.in.region.RegionService;
import com.codesquad.secondhand.domain.image.Image;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.product.Category;
import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import com.codesquad.secondhand.domain.region.Region;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ProductFacade implements ProductUseCase {

    private final ProductService productService;
    private final RegionService regionService;
    private final CategoryService categoryService;
    private final ImageService imageService;
    private final MemberService memberService;

    @Transactional
    @Override
    public ProductDetail getDetails(long id) {
        Product product = productService.getById(id);
        long writerId = product.getWriterId();
        Member member = memberService.getById(writerId);
        Long categoryId = product.getCategoryId();
        Category category = categoryService.getById(categoryId);
        long regionId = product.getRegionId();
        Region region = regionService.getById(regionId);
        return toProductDetail(product, category, member, region);
    }


    @Transactional
    @Override
    public long save(ProductCreateRequest productCreateRequest, Member member) {
        Product product = toProduct(productCreateRequest, member);
        return productService.save(product)
                .getId();
    }


    @Transactional
    @Override
    public void modify(long id, ProductModifyRequest productModifyRequest) {
        Product product = productService.getById(id);
        modifyProduct(productModifyRequest, product);
    }

    @Transactional
    @Override
    public void modifyStatus(long id, String status) {
        productService.modifyStatus(id, status);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductInfo> getProductsByRegion(long regionId) {
        List<Product> products = productService.getProductsByRegion(regionId);
        return toProductsInfo(products);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductInfo> getProductsByRegionAndCategory(long regionId, long categoryId) {
        List<Product> products = productService.getProductsByRegionAndCategory(regionId, categoryId);
        return toProductsInfo(products);
    }

    @Transactional
    @Override
    public void delete(long id) {
        productService.delete(id);
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
                member.getId(),
                category.getId(),
                thumbnailUrl,
                images,
                region.getId(),
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
                category.getId(),
                thumbnailUrl,
                images,
                region.getId());
    }

    private List<ProductInfo> toProductsInfo(List<Product> products) {
        return products.stream()
                .map(product -> toProductInfo(product, regionService.getById(product.getRegionId())))
                .collect(Collectors.toList());
    }

}

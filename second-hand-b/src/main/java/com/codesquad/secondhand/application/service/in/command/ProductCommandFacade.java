package com.codesquad.secondhand.application.service.in.command;

import com.codesquad.secondhand.adapter.in.web.command.product.request.ProductCreateRequest;
import com.codesquad.secondhand.adapter.in.web.command.product.request.ProductModifyRequest;
import com.codesquad.secondhand.application.port.in.command.ProductCommandUseCase;
import com.codesquad.secondhand.application.service.in.CategoryQueryService;
import com.codesquad.secondhand.application.service.in.prodcut.ImageService;
import com.codesquad.secondhand.application.service.in.prodcut.ProductService;
import com.codesquad.secondhand.application.service.in.common.utils.ProductUnits;
import com.codesquad.secondhand.application.service.in.region.RegionService;
import com.codesquad.secondhand.domain.product.Category;
import com.codesquad.secondhand.domain.product.Image;
import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import com.codesquad.secondhand.domain.region.Region;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ProductCommandFacade implements ProductCommandUseCase {

    private final ProductService productService;
    private final RegionService regionService;
    private final CategoryQueryService categoryService;
    private final ImageService imageService;

    @Transactional
    @Override
    public long save(ProductCreateRequest productCreateRequest, String validatedMemberId) {
        long memberId = Long.parseLong(validatedMemberId);
        Product product = toProduct(productCreateRequest, memberId);
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

    @Transactional
    @Override
    public void delete(long id) {
        productService.delete(id);
    }

    private Product toProduct(ProductCreateRequest productCreateRequest, long memberId) {
        Region region = regionService.getById(productCreateRequest.getRegionId());
        Category category = categoryService.getById(productCreateRequest.getCategoryId());

        List<Long> imagesId = productCreateRequest.getImagesId();
        List<Image> images = imageService.getImageListById(imagesId);
        String thumbnailUrl = ProductUnits.getThumbnailUrl(images);

        return new Product(productCreateRequest.getName(),
                productCreateRequest.getContent(),
                productCreateRequest.getPrice(),
                memberId,
                category,
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
                category,
                thumbnailUrl,
                images,
                region.getId());
    }
}

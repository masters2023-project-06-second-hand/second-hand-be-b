package com.codesquad.secondhand.command.service.in;

import com.codesquad.secondhand.command.adapter.in.web.product.request.ProductCreateRequest;
import com.codesquad.secondhand.command.adapter.in.web.product.request.ProductModifyRequest;
import com.codesquad.secondhand.command.port.in.ProductCommandUseCase;
import com.codesquad.secondhand.common.utils.ProductUnits;
import com.codesquad.secondhand.command.domain.product.Category;
import com.codesquad.secondhand.command.domain.product.Image;
import com.codesquad.secondhand.command.domain.product.Product;
import com.codesquad.secondhand.command.domain.product.Status;
import com.codesquad.secondhand.command.domain.region.Region;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ProductCommandFacade implements ProductCommandUseCase {

    private final ProductCommandService productCommandService;
    private final RegionCommandService regionCommandService;
    private final CategoryCommandService categoryCommandService;
    private final ImageCommandService imageCommandService;

    @Transactional
    @Override
    public long save(ProductCreateRequest productCreateRequest, String validatedMemberId) {
        long memberId = Long.parseLong(validatedMemberId);
        Product product = toProduct(productCreateRequest, memberId);
        return productCommandService.save(product)
                .getId();
    }


    @Transactional
    @Override
    public void modify(long id, ProductModifyRequest productModifyRequest) {
        Product product = productCommandService.getById(id);
        modifyProduct(productModifyRequest, product);
    }

    @Transactional
    @Override
    public void modifyStatus(long id, String status) {
        productCommandService.modifyStatus(id, status);
    }

    @Transactional
    @Override
    public void delete(long id) {
        productCommandService.delete(id);
    }

    private Product toProduct(ProductCreateRequest productCreateRequest, long memberId) {
        Region region = regionCommandService.getById(productCreateRequest.getRegionId());
        Category category = categoryCommandService.getById(productCreateRequest.getCategoryId());

        List<Long> imagesId = productCreateRequest.getImagesId();
        List<Image> images = imageCommandService.getImageListById(imagesId);
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
        Category category = categoryCommandService.getById(productModifyRequest.getCategoryId());

        List<Long> imagesId = productModifyRequest.getImagesId();
        List<Image> images = imageCommandService.getImageListById(imagesId);
        String thumbnailUrl = ProductUnits.getThumbnailUrl(images);

        Region region = regionCommandService.getById(productModifyRequest.getRegionId());
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

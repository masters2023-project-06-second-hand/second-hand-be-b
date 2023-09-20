package com.codesquad.secondhand.application.service.in.prodcut;

import static com.codesquad.secondhand.application.service.in.MemberUtils.validateMemberPermission;
import static com.codesquad.secondhand.application.service.in.prodcut.ProductMapper.toProductDetail;
import static com.codesquad.secondhand.application.service.in.prodcut.ProductMapper.toProductInfo;

import com.codesquad.secondhand.adapter.in.web.request.product.ProductCreateRequest;
import com.codesquad.secondhand.adapter.in.web.request.product.ProductModifyRequest;
import com.codesquad.secondhand.adapter.in.web.response.product.ProductDetail;
import com.codesquad.secondhand.adapter.in.web.response.product.ProductInfo;
import com.codesquad.secondhand.adapter.in.web.response.product.ProductsInfo;
import com.codesquad.secondhand.application.port.in.ProductUseCase;
import com.codesquad.secondhand.application.service.in.CategoryService;
import com.codesquad.secondhand.application.service.in.MemberService;
import com.codesquad.secondhand.application.service.in.region.RegionService;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.product.Category;
import com.codesquad.secondhand.domain.product.Image;
import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import com.codesquad.secondhand.domain.region.Region;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    public ProductsInfo getProductsByRegion(long regionId, Pageable pageable) {
        Slice<Product> products = productService.getProductsByRegion(regionId, pageable);
        return toProductsInfo(products);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductsInfo getProductsByRegionAndCategory(long regionId, long categoryId, Pageable pageable) {
        Slice<Product> products = productService.getProductsByRegionAndCategory(regionId, categoryId, pageable);
        return toProductsInfo(products);
    }

    @Transactional
    @Override
    public void delete(long id) {
        productService.delete(id);
    }


    @Override
    public ProductsInfo getMySellingProductsByStatus(Member member, long memberId, String statusName,
            Pageable pageable) {
        validateMemberPermission(member, memberId);
        Status status = Status.findByName(statusName);
        if (Status.isSoldOut(status)) {
            Slice<Product> products = productService.getSoldOutByWriterId(memberId, pageable);
            return toProductsInfo(products);
        }
        Slice<Product> products = productService.getSalesByWriterId(memberId, pageable);
        return toProductsInfo(products);
    }

    @Override
    public ProductsInfo getMySellingProducts(Member member, long memberId, Pageable pageable) {
        validateMemberPermission(member, memberId);
        Slice<Product> products = productService.getByWriterId(memberId, pageable);
        return toProductsInfo(products);
    }

    @Override
    public ProductsInfo fetchMemberFavoriteProducts(Member member, long memberId, Long categoryId, Pageable pageable) {
        validateMemberPermission(member, memberId);
        Slice<Product> products = productService.getLikesByMemberIdAndCategoryId(memberId, categoryId, pageable);
        return toProductsInfo(products);
    }

    @Transactional
    @Override
    public void toggleProductLikeStatus(Member member, Long productId, boolean isLiked) {
        Product product = productService.getById(productId);
        memberService.toggleProductLikeStatus(member, isLiked, product);
    }

    @Override
    public ProductsInfo fetchMemberFavoriteProducts(Member member, long memberId, Pageable pageable) {
        validateMemberPermission(member, memberId);
        Slice<Product> products = productService.getLikesByMemberId(memberId, pageable);
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

    private ProductsInfo toProductsInfo(Slice<Product> products) {
        List<ProductInfo> productInfoList = products.stream()
                .map(product -> toProductInfo(product, regionService.getById(product.getRegionId())))
                .collect(Collectors.toList());
        return new ProductsInfo(productInfoList, products.hasNext(), products.getNumber());
    }
}

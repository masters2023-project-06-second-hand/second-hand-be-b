package com.codesquad.secondhand.application.service.in;


import com.codesquad.secondhand.application.port.in.ProductUseCase;
import com.codesquad.secondhand.application.port.in.exception.ProductNotFoundException;
import com.codesquad.secondhand.application.port.in.request.ProductCreateRequest;
import com.codesquad.secondhand.application.port.in.request.ProductModifyRequest;
import com.codesquad.secondhand.application.port.in.response.ImageInfo;
import com.codesquad.secondhand.application.port.in.response.ProductDetail;
import com.codesquad.secondhand.application.port.in.response.ProductInfo;
import com.codesquad.secondhand.application.port.in.response.ProductWriter;
import com.codesquad.secondhand.application.port.out.ProductRepository;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {

    private static final int IMAGES_FIRST_INDEX = 0;

    private final ProductRepository productRepository;
    private final RegionService regionService;
    private final CategoryService categoryService;
    private final ImageService imageService;

    @Transactional
    @Override
    public Long save(ProductCreateRequest productCreateRequest, Member member) {
        Product product = toProduct(productCreateRequest, member);
        return productRepository.save(product).getId();
    }

    @Override
    public ProductDetail getDetails(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        return toProductDetail(product);
    }

    @Transactional
    @Override
    public void modify(Long id, ProductModifyRequest productModifyRequest) {
        Product product = productRepository.findById(id).orElseThrow();
        modifyProduct(productModifyRequest, product);
    }

    @Transactional
    @Override
    public void modifyStatus(Long id, String status) {
        Product product = productRepository.findById(id).orElseThrow();
        product.modifyStatus(status);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductInfo> getProductsByRegion(Long regionId) {
        List<Product> products = productRepository.findByRegionId(regionId);
        return toProductInfos(products);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductInfo> getProductsByRegionAndCategory(Long regionId, Long categoryId) {
        List<Product> products = productRepository.findByRegionIdAndCategoryId(regionId, categoryId);
        return toProductInfos(products);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Product product = getById(id);
        productRepository.deleteById(product.getId());
    }

    public Product getById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    public List<ProductInfo> getProductsByMemberId(long memberId) {
        return toProductInfos(productRepository.findProductsByMemberId(memberId));
    }

    public List<ProductInfo> getProductsByMemberIdAndCategoryId(long memberId, long categoryId) {
        return toProductInfos(productRepository.findProductsByMemberIdAndCategoryId(memberId, categoryId));
    }

    public List<ProductInfo> getByWriterId(long memberId) {
        return toProductInfos(productRepository.findByWriterId(memberId));
    }

    public List<ProductInfo> getSoldOutByWriterId(long memberId) {
        return toProductInfos(productRepository.findByWriterIdAndStatus(memberId, Status.SOLDOUT));
    }

    public List<ProductInfo> getSalesByWriterId(long memberId) {
        return toProductInfos(productRepository.findByWriterIdAndStatusNot(memberId, Status.SOLDOUT));
    }

    public List<ProductInfo> toProductInfos(List<Product> products) {
        return products.stream()
                .map(this::toProductInfo)
                .collect(Collectors.toList());
    }

    private Product toProduct(ProductCreateRequest productCreateRequest, Member member) {
        Region region = regionService.getById(productCreateRequest.getRegionId());
        Category category = categoryService.getById(productCreateRequest.getCategoryId());
        List<Long> imagesId = productCreateRequest.getImagesId();
        String thumbnailUrl = getThumbnailUrl(imagesId);
        List<Image> images = imageService.getImageListById(imagesId);
        return new Product(productCreateRequest.getName(),
                productCreateRequest.getContent(),
                productCreateRequest.getPrice(),
                member,
                category,
                thumbnailUrl,
                images,
                region,
                Status.ONSALES,
                LocalDateTime.now());
    }

    private ProductDetail toProductDetail(Product product) {
        Member member = product.getWriter();
        Category category = product.getCategory();
        Region region = product.getRegion();
        Status status = product.getStatus();
        List<ImageInfo> imageInfos = product.fetchImageInfos();
        return new ProductDetail(product.getId(),
                new ProductWriter(member.getId(), member.getNickname()),
                product.getName(),
                category.getName(),
                region.getName(),
                status.getName(),
                product.getContent(),
                product.getPrice(),
                imageInfos,
                product.getCreatedAt());
    }

    private ProductInfo toProductInfo(Product product) {
        Member member = product.getWriter();
        Region region = product.getRegion();
        Status status = product.getStatus();
        String thumbnailUrl = product.getThumbnailUrl();
        return new ProductInfo(product.getId(),
                member.getId(),
                thumbnailUrl,
                product.getName(),
                region.getName(),
                product.getCreatedAt(),
                status.getName(),
                product.getPrice(),
                0,
                0);
    }

    private void modifyProduct(ProductModifyRequest productModifyRequest, Product product) {
        Category category = categoryService.getById(productModifyRequest.getCategoryId());
        List<Long> imagesId = productModifyRequest.getImagesId();
        String thumbnailUrl = getThumbnailUrl(imagesId);
        List<Image> images = imageService.getImageListById(imagesId);
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

    private String getThumbnailUrl(List<Long> imagesId) {
        Image thumbnailImage = imageService.getById(imagesId.get(IMAGES_FIRST_INDEX));
        return thumbnailImage.getUrl();
    }
}

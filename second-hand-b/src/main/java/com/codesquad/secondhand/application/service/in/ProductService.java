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
import java.util.Set;
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
    public void modify(Long id, ProductModifyRequest request) {
        Product product = productRepository.findById(id).orElseThrow();
        Category category = categoryService.getById(request.getCategoryId());
        List<Long> imagesId = request.getImagesId();
        // 이미지 목록의 첫번째는 썸네일 이미지
        Image thumbnailImage = imageService.getById(imagesId.get(IMAGES_FIRST_INDEX));
        List<Image> images = imageService.getImageListById(request.getImagesId());
        Region region = regionService.getById(request.getRegionId());
        product.modifyProduct(request.getName(),
                request.getContent(),
                request.getPrice(),
                category,
                thumbnailImage.getUrl(),
                images,
                region);
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
        Set<Product> products = productRepository.findByRegionId(regionId);
        return toProductInfos(products);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductInfo> getProductsByRegionAndCategory(Long regionId, Long categoryId) {
        Set<Product> products = productRepository.findByRegionIdAndCategoryId(regionId, categoryId);
        return toProductInfos(products);
    }

    public Product getById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    public List<ProductDetail> getProductsByMemberIdAndCategoryId(long memberId, long categoryId) {
        return toProductDetails(productRepository.findProductsByMemberIdAndCategoryId(memberId, categoryId));
    }

    public List<ProductDetail> getByWriterId(long memberId) {
        return toProductDetails(productRepository.findByWriterId(memberId));
    }

    public List<ProductDetail> getSoldOutByWriterId(long memberId) {
        return toProductDetails(productRepository.findByWriterIdAndStatus(memberId, Status.SOLDOUT));
    }

    public List<ProductDetail> getSalesByWriterId(long memberId) {
        return toProductDetails(productRepository.findByWriterIdAndStatusNot(memberId, Status.SOLDOUT));
    }

    public List<ProductDetail> toProductDetails(Set<Product> products) {
        return products.stream()
                .map(this::toProductDetail)
                .collect(Collectors.toList());
    }

    public List<ProductInfo> toProductInfos(Set<Product> products) {
        return products.stream()
                .map(this::toProductInfo)
                .collect(Collectors.toList());
    }

    private Product toProduct(ProductCreateRequest productCreateRequest, Member member) {
        Region region = regionService.getById(productCreateRequest.getRegionId());
        Category category = categoryService.getById(productCreateRequest.getCategoryId());
        List<Long> imagesId = productCreateRequest.getImagesId();
        // 이미지 목록의 첫번째는 썸네일 이미지
        Image thumbnailImage = imageService.getById(imagesId.get(IMAGES_FIRST_INDEX));
        List<Image> images = imageService.getImageListById(imagesId);
        return new Product(productCreateRequest.getName(),
                productCreateRequest.getContent(),
                productCreateRequest.getPrice(),
                member,
                category,
                thumbnailImage.getUrl(),
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
}

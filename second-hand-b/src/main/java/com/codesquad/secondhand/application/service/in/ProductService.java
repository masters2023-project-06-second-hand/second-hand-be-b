package com.codesquad.secondhand.application.service.in;


import com.codesquad.secondhand.application.port.in.ProductUseCase;
import com.codesquad.secondhand.application.port.in.exception.ProductNotFoundException;
import com.codesquad.secondhand.application.port.in.request.ProductCreateRequest;
import com.codesquad.secondhand.application.port.in.request.ProductModifyRequest;
import com.codesquad.secondhand.application.port.in.response.ImageInfo;
import com.codesquad.secondhand.application.port.in.response.ProductDetail;
import com.codesquad.secondhand.application.port.out.ProductRepository;
import com.codesquad.secondhand.domain.image.Image;
import com.codesquad.secondhand.domain.product.Category;
import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import com.codesquad.secondhand.domain.region.Region;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Long save(ProductCreateRequest productCreateRequest, String email) {
        Product product = toProduct(productCreateRequest);
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
        List<Image> images = imageService.getImageListById(request.getImagesId());
        Region region = regionService.getById(request.getRegionId());
        product.modifyProduct(request.getName(),
                request.getContent(),
                request.getPrice(),
                category,
                images,
                region);
    }

    @Transactional
    @Override
    public void modifyStatus(Long id, String status) {
        Product product = productRepository.findById(id).orElseThrow();
        product.modifyStatus(status);
    }

    public static ProductDetail toProductDetail(Product product) {
        Category category = product.getCategory();
        Region region = product.getRegion();
        Status status = product.getStatus();
        List<ImageInfo> imageInfos = product.fetchImageInfos();
        return new ProductDetail(product.getId(),
                null,
                product.getName(),
                category.getName(),
                region.getName(),
                status.getName(),
                product.getContent(),
                product.getPrice(),
                imageInfos,
                product.getCreatedAt());
    }

    public Product getById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    public List<ProductDetail> getProductsByMemberIdAndCategoryId(long memberId, long categoryId) {
        return toProductDetails(productRepository.findProductsByMemberIdAndCategoryId(memberId, categoryId));
    }

    private Product toProduct(ProductCreateRequest productCreateRequest) {
        // TODO: jwt id를 이용해서 writer 추가
        Region region = regionService.getById(productCreateRequest.getRegionId());
        Category category = categoryService.getById(productCreateRequest.getCategoryId());
        List<Long> imagesId = productCreateRequest.getImagesId();
        // 이미지 목록의 첫번째는 썸네일 이미지
        Image thumbnailImage = imageService.getById(imagesId.get(IMAGES_FIRST_INDEX));
        List<Image> images = imageService.getImageListById(imagesId);
        return new Product(productCreateRequest.getName(),
                productCreateRequest.getContent(),
                productCreateRequest.getPrice(),
                null,
                category,
                thumbnailImage,
                images,
                region,
                Status.ONSALES,
                LocalDateTime.now());
    }

    public List<ProductDetail> toProductDetails(Set<Product> products) {
        return products.stream()
                .map(ProductService::toProductDetail)
                .collect(Collectors.toList());
    }
}

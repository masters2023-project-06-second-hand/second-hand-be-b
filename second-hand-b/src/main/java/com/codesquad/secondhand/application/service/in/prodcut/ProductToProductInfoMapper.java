package com.codesquad.secondhand.application.service.in.prodcut;

import com.codesquad.secondhand.application.port.in.response.ProductInfo;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import com.codesquad.secondhand.domain.region.Region;
import java.util.List;
import java.util.stream.Collectors;

public class ProductToProductInfoMapper {

    private ProductToProductInfoMapper() {
        throw new UnsupportedOperationException();
    }

    public static ProductInfo map(Product product) {
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

    public static List<ProductInfo> map(List<Product> products) {
        return products.stream()
                .map(ProductToProductInfoMapper::map)
                .collect(Collectors.toList());
    }
}

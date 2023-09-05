package com.codesquad.secondhand.domain.product;

import com.codesquad.secondhand.application.port.in.response.ImageInfo;
import com.codesquad.secondhand.domain.image.Image;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.region.Region;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product")
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private int price;
    @ManyToOne
    @JoinColumn(name = "writer_id")
    private Member writer;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Embedded
    private Images images = new Images();
    @ManyToOne
    @JoinColumn(name = "thumbnail_id")
    private Image thumbnailImage;
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Product(String name, String content, int price, Member writer, Category category, Image thumbnailImage,
            List<Image> images, Region region, Status status, LocalDateTime createdAt) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.writer = writer;
        this.category = category;
        this.thumbnailImage = thumbnailImage;
        modifyImages(images);
        this.region = region;
        this.status = status;
        this.createdAt = createdAt;
    }

    public void modifyProduct(String name, String content, int price, Category category, List<Image> images,
            Region region) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.category = category;
        modifyImages(images);
        this.region = region;
    }

    public void modifyStatus(String status) {
        this.status = Status.findByName(status);
    }

    public void modifyImages(List<Image> images) {
        this.images.modify(images);
    }

    public List<ImageInfo> fetchImageInfos() {
        return images.getImageList().stream()
                .map(image -> new ImageInfo(image.getId(), image.getUrl()))
                .collect(Collectors.toUnmodifiableList());
    }
}

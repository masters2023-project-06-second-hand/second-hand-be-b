package com.codesquad.secondhand.domain.product;

import com.codesquad.secondhand.domain.image.Image;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product")
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
    @Column(nullable = false)
    private Long writerId;
    private Long categoryId;
    @Embedded
    private Images images = new Images();
    @JoinColumn(name = "thumbnail_id", nullable = false)
    private String thumbnailUrl;
    @Column(nullable = false)
    private Long regionId;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Product(String name, String content, int price, long writerId, Long categoryId, String thumbnailUrl,
            List<Image> images, long regionId, Status status, LocalDateTime createdAt) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.writerId = writerId;
        this.categoryId = categoryId;
        this.thumbnailUrl = thumbnailUrl;
        this.images = new Images(images);
        this.regionId = regionId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public void modifyProduct(String name, String content, int price, Long categoryId, String thumbnailUrl,
            List<Image> images, long regionId) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.categoryId = categoryId;
        this.thumbnailUrl = thumbnailUrl;
        this.images.modify(images);
        this.regionId = regionId;
    }

    public void modifyStatus(String status) {
        this.status = Status.findByName(status);
    }

    public List<Image> fetchImages() {
        return images.getImageList();
    }

    public Long getId() {
        return id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public long getRegionId() {
        return regionId;
    }

    public Status getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public int getPrice() {
        return price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public long getWriterId() {
        return writerId;
    }
}

package com.codesquad.secondhand.domain.product;

import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.region.Region;
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
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Enumerated(EnumType.STRING)
    private Category category;
    @Embedded
    private Images images;
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    public Product(String name, String content, int price, Member writer, Category category, Images images,
            Region region) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.writer = writer;
        this.category = category;
        this.images = images;
        this.region = region;
    }

    public Long getId() {
        return id;
    }
}

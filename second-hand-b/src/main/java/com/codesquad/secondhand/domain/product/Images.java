package com.codesquad.secondhand.domain.product;

import com.codesquad.secondhand.domain.image.Image;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Images {

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(name = "product_image",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private List<Image> imageList = new ArrayList<>();

    public Images(List<Image> images) {
        this.imageList = images;
    }

    public void modify(List<Image> images) {
        imageList = images;
    }

    public List<Image> getImageList() {
        return imageList;
    }
}

package com.codesquad.secondhand.domain.product;

import com.codesquad.secondhand.domain.image.Image;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Images {

    @ElementCollection
    @CollectionTable(name = "product_image",
            joinColumns = @JoinColumn(name = "product_id"))
    private List<Image> imageList = new ArrayList<>();

    public List<Image> getImageList() {
        return imageList;
    }

    public void modify(List<Image> images) {
        imageList = images;
    }
}

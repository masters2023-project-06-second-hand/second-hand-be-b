package com.codesquad.secondhand.domain.product;

import com.codesquad.secondhand.domain.image.Image;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Images {

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Image> imageList = new ArrayList<>();

    public List<Image> getImageList() {
        return imageList;
    }

    public void modify(List<Image> images) {
        imageList = images;
    }
}

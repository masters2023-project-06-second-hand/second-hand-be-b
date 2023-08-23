package com.codesquad.secondhand.domain.product;

import com.codesquad.secondhand.domain.image.Image;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Images {
    @OneToMany(mappedBy = "id")
    private List<Image> imageList;
}

package com.codesquad.secondhand.domain.member;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Likes implements Serializable {

    private static final long serialVersionUID = 1905122041950251207L;

    @ElementCollection
    private List<Long> productsId = new ArrayList<>();

    public boolean add(long productId) {
        return productsId.add(productId);
    }

    public boolean remove(long productId) {
        return productsId.remove(productId);
    }

    public List<Long> getProducts() {
        return Collections.unmodifiableList(productsId);
    }
}

package com.codesquad.secondhand.domain.member;

import com.codesquad.secondhand.domain.product.Product;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.ManyToMany;

@Embeddable
public class Likes {

    @ManyToMany
    private List<Product> products;

    public boolean add(Product product) {
        return products.add(product);
    }

    public boolean remove(Product product) {
        return products.remove(product);
    }
}

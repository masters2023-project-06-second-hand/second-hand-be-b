package com.codesquad.secondhand.domain.member;

import com.codesquad.secondhand.domain.product.Product;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Embeddable;
import javax.persistence.ManyToMany;

@Embeddable
public class Likes {

    @ManyToMany
    private Set<Product> products;

    public boolean add(Product product) {
        return products.add(product);
    }

    public boolean remove(Product product) {
        return products.remove(product);
    }


    public Set<Product> getProducts() {
        return products;
    }

    public Set<Product> getProductsByCategoryId(long categoryId) {
        return products.stream()
                .filter(product -> product.getCategory().isSameId(categoryId))
                .collect(Collectors.toSet());
    }
}

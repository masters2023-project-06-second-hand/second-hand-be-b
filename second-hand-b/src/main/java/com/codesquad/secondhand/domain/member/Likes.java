package com.codesquad.secondhand.domain.member;

import com.codesquad.secondhand.domain.product.Product;
import java.util.List;
import java.util.stream.Collectors;
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


    public List<Product> getProducts() {
        return products;
    }

    public List<Product> getProductsByCategoryId(long categoryId) {
        return products.stream()
                .filter(product -> product.getCategory().isSameId(categoryId))
                .collect(Collectors.toList());
    }
}

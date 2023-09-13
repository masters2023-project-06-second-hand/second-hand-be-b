package com.codesquad.secondhand.domain.member;

import com.codesquad.secondhand.domain.product.Product;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Embeddable
public class Likes {

    @ManyToMany
    @JoinTable(name = "member_likes_product",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();

    public boolean add(Product product) {
        return products.add(product);
    }

    public boolean remove(Product product) {
        return products.remove(product);
    }
}

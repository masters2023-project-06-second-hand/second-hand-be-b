package com.codesquad.secondhand.application.port.out;

import com.codesquad.secondhand.domain.product.Category;
import java.util.List;

public interface CategoryRepository {

    void saveAll(List<Category> categories);
}

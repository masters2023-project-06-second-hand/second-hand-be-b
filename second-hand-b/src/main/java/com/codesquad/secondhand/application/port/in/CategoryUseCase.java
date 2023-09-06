package com.codesquad.secondhand.application.port.in;

import com.codesquad.secondhand.application.port.in.response.CategoryDetailWithImg;
import com.codesquad.secondhand.application.port.in.response.CategorySimpleDetail;
import java.util.List;

public interface CategoryUseCase {

    List<CategoryDetailWithImg> getCategoriesWithImgUrl();

    List<CategorySimpleDetail> getCategories();
}

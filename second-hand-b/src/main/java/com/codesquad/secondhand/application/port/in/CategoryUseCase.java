package com.codesquad.secondhand.application.port.in;

import com.codesquad.secondhand.application.port.in.response.CategoryDetail;
import com.codesquad.secondhand.application.port.in.response.CategorySimpleDetail;
import java.util.List;

public interface CategoryUseCase {

    List<CategoryDetail> getCategoriesWithImgUrl();

    List<CategorySimpleDetail> getCategories();
}

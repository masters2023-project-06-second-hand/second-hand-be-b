package com.codesquad.secondhand.application.port.in;

import com.codesquad.secondhand.adapter.in.web.response.CategoryDetail;
import com.codesquad.secondhand.adapter.in.web.response.CategorySimpleDetail;
import java.util.List;

public interface CategoryUseCase {

    List<CategoryDetail> getCategoriesWithImgUrl();

    List<CategorySimpleDetail> getCategories();
}

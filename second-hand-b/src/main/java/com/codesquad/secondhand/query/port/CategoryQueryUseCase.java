package com.codesquad.secondhand.query.port;

import com.codesquad.secondhand.query.controller.prodcut.response.CategoryDetail;
import com.codesquad.secondhand.query.controller.prodcut.response.CategorySimpleDetail;
import java.util.List;

public interface CategoryQueryUseCase {

    List<CategoryDetail> getCategoriesWithImgUrl();

    List<CategorySimpleDetail> getCategories();
}

package com.codesquad.secondhand.application.port.in;

import com.codesquad.secondhand.adapter.in.web.query.prodcut.response.CategoryDetail;
import com.codesquad.secondhand.adapter.in.web.query.prodcut.response.CategorySimpleDetail;
import java.util.List;

public interface CategoryQueryUseCase {

    List<CategoryDetail> getCategoriesWithImgUrl();

    List<CategorySimpleDetail> getCategories();
}

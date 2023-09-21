package com.codesquad.secondhand.command.port.in;

import com.codesquad.secondhand.command.adapter.in.web.product.request.ProductCreateRequest;
import com.codesquad.secondhand.command.adapter.in.web.product.request.ProductModifyRequest;

public interface ProductCommandUseCase {

    long save(ProductCreateRequest productCreateRequest, String validatedMemberId);

    void modify(long id, ProductModifyRequest productModifyRequest);

    void modifyStatus(long id, String status);

    void delete(long id);
}

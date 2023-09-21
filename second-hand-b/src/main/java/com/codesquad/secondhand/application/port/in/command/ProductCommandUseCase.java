package com.codesquad.secondhand.application.port.in.command;

import com.codesquad.secondhand.adapter.in.web.command.product.request.ProductCreateRequest;
import com.codesquad.secondhand.adapter.in.web.command.product.request.ProductModifyRequest;

public interface ProductCommandUseCase {

    long save(ProductCreateRequest productCreateRequest, String validatedMemberId);

    void modify(long id, ProductModifyRequest productModifyRequest);

    void modifyStatus(long id, String status);

    void delete(long id);
}

package com.codesquad.secondhand.command.adapter.in.web.product.annotation;

import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImagesIdValidator implements ConstraintValidator<ImagesId, List<Long>> {

    private static final int ZERO = 0;

    @Override
    public boolean isValid(List<Long> ids, ConstraintValidatorContext context) {
        if (ids == null) {
            return true;
        }
        return ids.stream().allMatch(id -> id > ZERO);
    }
}


package com.codesquad.secondhand.adapter.in.web.request.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Min;

@Min(value = 0)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface ProductPrice {

    String message() default "{product.price.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

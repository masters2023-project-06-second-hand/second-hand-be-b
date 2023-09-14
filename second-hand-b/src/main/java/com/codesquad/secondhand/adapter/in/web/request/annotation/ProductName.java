package com.codesquad.secondhand.adapter.in.web.request.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NotBlank
@Size(min = 3, max = 50)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface ProductName {

    String message() default "{product.name.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

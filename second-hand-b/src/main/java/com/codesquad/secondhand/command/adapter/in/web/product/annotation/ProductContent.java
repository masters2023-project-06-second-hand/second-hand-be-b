package com.codesquad.secondhand.command.adapter.in.web.product.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NotBlank
@Size(min = 3, max = 1000)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface ProductContent {

    String message() default "{product.content.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

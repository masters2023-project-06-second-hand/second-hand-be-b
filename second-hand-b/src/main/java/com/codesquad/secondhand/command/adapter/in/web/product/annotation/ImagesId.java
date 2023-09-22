package com.codesquad.secondhand.command.adapter.in.web.product.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NotNull
@Size(min = 1)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ImagesIdValidator.class})
public @interface ImagesId {

    String message() default "{images.id.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

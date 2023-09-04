package com.codesquad.secondhand.oauth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = TestSecurityContextFactory.class)
public @interface WithTestUser {

    String username() default "testuser";

    String email() default "test@email.com";

    String[] roles() default "USER";
}

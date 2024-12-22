package com.vn.shoplaptopp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {RegisterValidator.class})
public @interface RegisterChecked {
    String message() default "{Đăng ký không hợp lệ}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

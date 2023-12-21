package com.chennann.library.anno;

import com.chennann.library.Validation.BookCopyLocationValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {BookCopyLocationValidation.class})
public @interface BookCopyLocation {

    String message() default "location的值只能是'图书流通室'或者'图书阅览室'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

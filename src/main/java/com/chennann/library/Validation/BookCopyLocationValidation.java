package com.chennann.library.Validation;

import com.chennann.library.anno.BookCopyLocation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BookCopyLocationValidation implements ConstraintValidator<BookCopyLocation, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }
        if (s.equals("图书流通室") || s.equals("图书阅览室")) {
            return true;
        }
        return false;
    }
}

package com.example.CuoiKy.validator;


import com.example.CuoiKy.entity.Book;
import com.example.CuoiKy.validator.annotation.ValidBookId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidBookIdValidator implements ConstraintValidator<ValidBookId, Book> {
    @Override
    public boolean isValid(Book book, ConstraintValidatorContext context){
        return book != null && book.getId() != null;
    }
}
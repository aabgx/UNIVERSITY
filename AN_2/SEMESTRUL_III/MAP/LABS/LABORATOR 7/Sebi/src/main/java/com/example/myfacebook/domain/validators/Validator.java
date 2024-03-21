package com.example.myfacebook.domain.validators;

import com.example.myfacebook.exceptions.ValidationException;

/**
 *
 * @param <T>
 */
public interface Validator<T> {
    /**
     *
     * @param entity
     * @throws ValidationException
     */
    void validate(T entity) throws ValidationException;
}

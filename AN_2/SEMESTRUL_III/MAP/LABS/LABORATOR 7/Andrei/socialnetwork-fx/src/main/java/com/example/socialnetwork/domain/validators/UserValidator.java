package com.example.socialnetwork.domain.validators;

import com.example.socialnetwork.domain.User;

public class UserValidator implements Validator<User> {
    @Override
    public void validate(User entity) throws ValidationException {
        if(entity.getFirstName().equals("") || entity.getLastName().equals(""))
            throw new ValidationException("First name and last name must not be empty!");
    }
}

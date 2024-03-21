package com.example.myfacebook.domain.validators;

import com.example.myfacebook.domain.User;
import com.example.myfacebook.exceptions.ValidationException;

public class UserValidator implements Validator<User>{
    public void validate(User user)throws ValidationException
    {
        String message="";
        if(user.getUsername()=="")
            message+="Username can not be empty.\n";
        if(user.getCity()=="")
            message+="City can not be empty.\n";
        if(user.getSex()=="")
            message+="Sex can not be empty.\n";

        if(!message.isEmpty()){
            throw new ValidationException(message);
        }
    }
}
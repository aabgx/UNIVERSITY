package com.example.lab6.validator;

import com.example.lab6.exception.CustomException;

import java.util.regex.Pattern;

public class Validator {
    public final Pattern textPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
    public Validator() {
    }
    public void validateUser(String userName, String userPassword) throws CustomException
    {
        CustomException e;
        if(userName.equals("") || userPassword.equals(""))
        {
            e = new CustomException("Toate campurile trebuie introduse!");
            throw e;
        }
        if(userName.matches(".*\\d.*"))
        {
            e = new CustomException("Numele utilizatorului nu poate contine un numar!.");
            throw e;
        }
        boolean corect = textPattern.matcher(userPassword).matches();
        if(userPassword.contains(" ") || !corect)
        {
            e = new CustomException("Parola utilizatorului trebuie nu poate contine spatii, trebuie sa contina cel putin un numar si atat majuscule cat si minuscule!");
            throw e;
        }
    }
}

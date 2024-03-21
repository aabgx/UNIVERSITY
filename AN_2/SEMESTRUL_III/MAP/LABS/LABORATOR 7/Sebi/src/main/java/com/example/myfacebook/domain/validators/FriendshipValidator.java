package com.example.myfacebook.domain.validators;

import com.example.myfacebook.domain.Friendship;
import com.example.myfacebook.exceptions.ValidationException;

public class FriendshipValidator implements Validator<Friendship> {
    @Override
    public void validate(Friendship entity) throws ValidationException {
        String message="";
        if(entity.getFriendsFrom()=="")
            message+="date can not be empty.\n";

    }
}

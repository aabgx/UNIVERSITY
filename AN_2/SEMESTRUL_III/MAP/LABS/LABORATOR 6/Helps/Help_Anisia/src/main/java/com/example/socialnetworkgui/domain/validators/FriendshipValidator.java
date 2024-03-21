package com.example.socialnetworkgui.domain.validators;

import com.example.socialnetworkgui.domain.Friendship;
import com.example.socialnetworkgui.domain.exceptions.ValidationException;

import java.util.Objects;

public class FriendshipValidator implements Validator<Friendship>{
    /**
     * Validates a Friendship entity
     * @param entity, Friendship, friendship to be validates
     * @throws ValidationException if friendship is not valid
     */
    @Override
    public void validate(Friendship entity) throws ValidationException {
        String errors="";
        if(entity.getId().getFirst()==null){
            errors+="First id cannot be null!\n";
        }
        if(entity.getId().getSecond()==null){
            errors+="Second id cannot be null!\n";
        }
        if(Objects.equals(entity.getId().getFirst(), entity.getId().getSecond())){
            errors+="User cannot be friends with itself!\n";
        }
        /*if(repo.findOne(entity.getId().getFirst())==null){
            errors+="User with id:" + entity.getId().getFirst()+" does not exist!\n";
        }
        if(repo.findOne(entity.getId().getSecond())==null){
            errors+="User with id: " +entity.getId().getSecond()+" does not exist!\n";
        }*/
        if(entity.getDate()==null) errors+="Date cannot be null!\n";
        if(errors.length()>0) throw new ValidationException(errors);
    }
}

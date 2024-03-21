package com.example.socialnetworkgui.domain.validators;

import com.example.socialnetworkgui.domain.Request;
import com.example.socialnetworkgui.domain.exceptions.ValidationException;

import java.util.Objects;

public class RequestValidator implements Validator<Request>{
    @Override
    public void validate(Request entity) throws ValidationException {
        String errors="";
        if(entity.getId().getFirst()==null){
            errors+="Id1 cannot be null!\n";
        }
        if(entity.getId().getSecond()==null){
            errors+="Id2 cannot be null!\n";
        }
        if(Objects.equals(entity.getId().getFirst(), entity.getId().getSecond())){
            errors+="User cannot be friends with itself!\n";
        }
        if(entity.getSentAt()==null){
            errors+="Date cannot be null!\n";
        }
        if(entity.getStatus().equals(" ")){
            errors+="Status cannot be null!\n";
        }

        if(errors.length()>0) throw new ValidationException(errors);
    }
}

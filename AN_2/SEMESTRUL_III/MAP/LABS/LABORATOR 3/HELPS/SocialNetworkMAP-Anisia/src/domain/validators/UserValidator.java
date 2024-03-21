package domain.validators;

import domain.User;
import domain.exceptions.ValidationException;

import java.util.Objects;

public class UserValidator implements Validator<User>{

    /**
     * Validates User entity
     * @param entity, User, user to be validates
     * @throws ValidationException if user is not valid
     */
    @Override
    public void validate(User entity) throws ValidationException {
        String errors= "";
        if(Objects.equals(entity.getFirstName(), "")|| Objects.equals(entity.getLastName(), "")){
            errors+="Name cannot be null!\n";
        }
        if(entity.getFirstName().matches("[0-9]+")||entity.getFirstName().matches(" ")){
            errors+="First name cannot contain spaces or numbers!\n";
        }
        if(entity.getLastName().contains(" ")||entity.getLastName().matches("[0-9]+")){
            errors+="Last name cannot contain spaces or numbers!\n";
        }
        if(entity.getEmail().equals("")){
            errors+="Email cannto be null!";
        }
        if(!entity.getEmail().matches("[a-z]+@email.com$")){
            errors+="Email is not valid!\n";
        }
        if(errors.length()>0) throw new ValidationException(errors);
    }
}

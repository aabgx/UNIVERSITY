package socialnetwork.com.validators;

import socialnetwork.com.domain.User;

public class ValidatorUtilizator implements Validator<User> {


    /***
     * Valideaza un utilizator
     * @param entity Entitatea care va fi validatq
     * @throws ValidationException Daca utilizatorul nu e valid
     */
    @Override
    public void validate(User entity) throws ValidationException {
        if(entity.getLastname()==null || entity.getLastname().equals(""))
            throw new ValidationException("Nume Invalid!");
        if(entity.getFirstname()==null || entity.getFirstname().equals(""))
            throw new ValidationException("Prenume Invalid!");
        if(entity.getEmail()==null || entity.getEmail().equals(""))
            throw new ValidationException("Email Invalid!");
        if(entity.getPassword()==null || entity.getPassword().equals(""))
            throw new ValidationException("Parola Invalida!");
        if(entity.get_id()==null || entity.get_id().equals(""))
            throw new ValidationException("ID Invalid!");
    }

}

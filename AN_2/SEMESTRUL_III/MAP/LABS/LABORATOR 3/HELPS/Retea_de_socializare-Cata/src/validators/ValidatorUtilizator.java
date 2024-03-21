package validators;

import domain.Utilizator;

public class ValidatorUtilizator implements Validator<Utilizator> {


    @Override
    public void validate(Utilizator entity) throws ValidationException {
        if(entity.get_lastname()==null || entity.get_lastname().equals(""))
            throw new ValidationException("Nume Invalid!");
        if(entity.get_firstname()==null || entity.get_firstname().equals(""))
            throw new ValidationException("Prenume Invalid!");
        if(entity.get_email()==null || entity.get_email().equals(""))
            throw new ValidationException("Email Invalid!");
        if(entity.get_parola()==null || entity.get_parola().equals(""))
            throw new ValidationException("Parola Invalida!");
        if(entity.get_id()==null || entity.get_id().equals(""))
            throw new ValidationException("ID Invalid!");
    }
}

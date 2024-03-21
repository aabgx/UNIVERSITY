package validators;

import domain.Prietenie;

public class ValidatorPrietenie implements Validator<Prietenie> {
    @Override
    public void validate(Prietenie entity) throws ValidationException {
        if(entity.get_prieten_1()==null || entity.get_prieten_1().equals(""))
            throw new ValidationException("Id prieten 1 Invalid!");
        if(entity.get_prieten_2()==null || entity.get_prieten_2().equals(""))
            throw new ValidationException("Id prieten 2 Invalid!");
        if(entity.get_id()==null || entity.get_id().equals(""))
            throw new ValidationException("Id Invalid!");
    }
}

package socialnetwork.com.validators;

import socialnetwork.com.domain.Friend;

public class ValidatorPrietenie implements Validator<Friend> {
    /***
     * Suprascrie metoda
     * @param entity Entitatea care va fi validatq
     * @throws ValidationException Daca nu este valida prietenia
     */
    @Override
    public void validate(Friend entity) throws ValidationException {
        if(entity.getPrieten1()==null || entity.getPrieten1().equals(0))
            throw new ValidationException("Id prieten 1 Invalid!");
        if(entity.getPrieten2()==null || entity.getPrieten2().equals(0))
            throw new ValidationException("Id prieten 2 Invalid!");
        if(entity.get_id()==null || entity.get_id().equals(0))
            throw new ValidationException("Id Invalid!");
    }
}

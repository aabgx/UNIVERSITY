package com.domain.validators;

import com.domain.Prietenie;

public class ValidatorPrietenie implements Validator<Prietenie>{
    /***
     * Validator pt Prietenie
     * @param entity - entitate Prietenie, de validat
     * @throws ValidationException daca nu e valida
     */
    @Override
    public void validate(Prietenie entity) throws ValidationException {
        if(entity.getIdPrieten1()==null)
            throw new ValidationException("\nId prieten 1 Invalid!\n");
        if(entity.getIdPrieten2()==null)
            throw new ValidationException("\nId prieten 2 Invalid!\n");
        if(entity.getId()==null)
            throw new ValidationException("\nId Invalid!\n");
    }
}

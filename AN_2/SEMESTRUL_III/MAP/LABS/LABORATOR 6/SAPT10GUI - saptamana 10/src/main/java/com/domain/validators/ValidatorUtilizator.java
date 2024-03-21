package com.domain.validators;

import com.domain.Utilizator;

public class ValidatorUtilizator implements Validator<Utilizator>{
    /***
     * Validator pt Utilizator
     * @param entity - entitatea Utilizator
     * @throws ValidationException daca nu e valid
     */
    @Override
    public void validate(Utilizator entity) throws ValidationException {
        //trim taie spatii de dinainte si dupa
        String nume = entity.getNume().trim();
        if (nume == null || nume.isEmpty() || nume.charAt(0) < 'A' || nume.charAt(0) > 'Z') {
            throw new ValidationException("\nNume invalid!\n");
        }
        String prenume = entity.getPrenume().trim();
        if (prenume == null || prenume.isEmpty() || prenume.charAt(0) < 'A' || prenume.charAt(0) > 'Z') {
            throw new ValidationException("\nPrenume invalid!\n");
        }

        //sa scriu ceva si pentru adresa de mail
        if(entity.getEmail()==null || entity.getEmail().equals(""))
            throw new ValidationException("\nEmail Invalid!\n");
        if(entity.getParola()==null || entity.getParola().equals(""))
            throw new ValidationException("\nParola Invalida!\n");
    }

}

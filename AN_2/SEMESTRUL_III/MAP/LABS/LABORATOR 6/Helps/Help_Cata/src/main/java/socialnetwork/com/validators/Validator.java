package socialnetwork.com.validators;

public interface Validator<T>{
    /***
     * Valideaza o entitate
     * @param entity Entitatea care va fi validatq
     * @throws ValidationException Daca nu este valida
     */
    void validate(T entity)throws ValidationException;
}


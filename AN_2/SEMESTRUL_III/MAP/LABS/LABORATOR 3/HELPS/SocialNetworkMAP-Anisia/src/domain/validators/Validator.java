package domain.validators;

import domain.exceptions.ValidationException;

public interface Validator<E> {
    /**
     * Validates entity
     * @param entity, E, entity to be validated
     * @throws ValidationException if entity is not valid
     */
    public void validate(E entity) throws ValidationException;
}

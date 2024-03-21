namespace Lab8_CSharp.model.Validator;

interface IValidator<E>
{
    void validate(E e);
}
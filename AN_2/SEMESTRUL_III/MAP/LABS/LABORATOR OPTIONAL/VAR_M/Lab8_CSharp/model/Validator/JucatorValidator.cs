using System.ComponentModel.DataAnnotations;
using Lab8_CSharp.model;
using Lab8_CSharp.model.Validator;

class JucatorValidator : IValidator<Jucator>
{
    public void validate(Jucator j)
    {
        bool valid = true;
        string mesajValidare = "";
        if (valid == false)
        {
            throw new ValidationException(mesajValidare);
        }
    }
}
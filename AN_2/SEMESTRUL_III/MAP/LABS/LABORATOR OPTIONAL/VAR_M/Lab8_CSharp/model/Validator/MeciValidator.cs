using System.ComponentModel.DataAnnotations;
using Lab8_CSharp.model;
using Lab8_CSharp.model.Validator;

class MeciValidator: IValidator<Meci>
{
    public void validate(Meci m)
    {
        bool valid = true;
        //to add
        string mesajValidare = "";
        if (valid == false)
        {
            throw new ValidationException(mesajValidare);
        }
    }
}
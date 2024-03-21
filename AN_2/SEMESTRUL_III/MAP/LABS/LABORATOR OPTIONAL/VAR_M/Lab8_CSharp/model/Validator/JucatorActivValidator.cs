using System.ComponentModel.DataAnnotations;
using Lab8_CSharp.model;
using Lab8_CSharp.model.Validator;

class JucatorActivValidator : IValidator<JucatorActiv>
{
    public void validate(JucatorActiv j)
    {
        bool valid = true;
        string mesajValidare = "";
        if (j.nrPuncteInscrise < 0)
        {
            valid = false;
            mesajValidare += "Numarul de puncte inscrise nu poate fi negativ.";
        }
        if (valid == false)
        {
            throw new ValidationException(mesajValidare);
        }
    }
}
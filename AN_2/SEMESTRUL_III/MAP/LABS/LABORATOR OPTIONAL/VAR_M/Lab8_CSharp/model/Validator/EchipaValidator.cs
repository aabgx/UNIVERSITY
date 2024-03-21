using System.ComponentModel.DataAnnotations;

namespace Lab8_CSharp.model.Validator;

class EchipaValidator:IValidator<Echipa>
{
    public void validate(Echipa e)
    {
        bool valid = true;
        string mesajValidare = "";
        if (e.Nume.Length < 3)
        {
            valid = false;
            mesajValidare += "Numele echipei trebuie sa aiba cel putin 3 caractere.\n";
        }
        if (valid == false)
        {
            throw new ValidationException(mesajValidare);
        }
    }
}

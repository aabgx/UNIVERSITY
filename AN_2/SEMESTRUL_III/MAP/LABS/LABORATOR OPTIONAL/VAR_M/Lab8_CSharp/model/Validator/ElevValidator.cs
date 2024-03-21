using System.ComponentModel.DataAnnotations;
namespace Lab8_CSharp.model.Validator;

class ElevValidator:IValidator<Elev>
{
    public void validate(Elev e)
    {
        bool valid = true;
        string mesajValidare = "";
        if (e.Nume.Length<7)
        {
            valid=false;
            mesajValidare+="Numele elevului trebuie sa aiba cel putin 7 caractere.\n";
        }
        if (e.Scoala.Length < 10)
        {
            valid = false;
            mesajValidare+="Numele scolii de provenienta trebuie sa aiba cel putin 10 caractere.\n";
        }
        if (valid == false)
        {
            throw new ValidationException(mesajValidare);
        }
    }
}
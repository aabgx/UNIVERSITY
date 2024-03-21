namespace Lab8_CSharp.model;

class Echipa:Entity<string>
{
    public String Nume { get; set; }
    
    
    
    public override string ToString()
    {
        return ID + " " + Nume;
    }
}
namespace Lab8_CSharp.model;

class Elev:Entity<string>
{
    public String Nume { get; set; }
    public String Scoala { get; set; }
    
    
    
    public override string ToString()
    {
        return ID + " " + Nume + " " + Scoala;
    }
}
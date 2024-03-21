namespace Lab8_CSharp.model;

class Jucator:Elev
{
    public Echipa Echipa { get; set; }

    
    public override string ToString()
    {
        return Nume + " ; " + Scoala;
    }
}
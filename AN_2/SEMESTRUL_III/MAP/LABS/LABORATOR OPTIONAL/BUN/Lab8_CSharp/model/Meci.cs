namespace Lab8_CSharp.model;

class Meci:Entity<string>
{
    public Echipa Echipa1 { get; set; }
    public Echipa Echipa2 { get; set; }
    public DateTime Date { get; set; }


    public override string ToString()
    {
        return Echipa1.Nume + ";" + Echipa2.Nume + ";" +
               Date.ToString("d/M/yyyy", System.Globalization.CultureInfo.InvariantCulture);
    }
}
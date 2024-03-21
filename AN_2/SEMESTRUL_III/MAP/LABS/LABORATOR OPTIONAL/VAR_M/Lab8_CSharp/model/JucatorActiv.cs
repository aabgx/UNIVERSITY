namespace Lab8_CSharp.model;

enum PlayerType
{
    Rezerva,Participant
}
class JucatorActiv:Entity<string>
{
    public String idMeci { get; set; }
    public int nrPuncteInscrise { get; set; }
    public PlayerType tip { get; set; }


    public override string ToString()
    {
        return ID + " ; " + nrPuncteInscrise + " ; " + tip;
    }
}
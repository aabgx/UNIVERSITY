using Model;

namespace services;

public interface IServices
{
    bool login(string username, string parola, IObserver client);

    void logout(string username);
    Utilizator getByAccount(string username,string parola);
    void createAccount(String username, String parola);
    List<int> creareVectorCnt();
    List<int> gasesteParticipanti(String proba, String categorie);
    String getNumeVarstaById(int id);
    List<String> gasesteNumeVarstaCopii(List<int> listCoduri);
    void adaugaCopilInscriere(String nume, int varsta, List<String> probe,String usernameCurent);
    List<string> JoinInscrieriCopii(String proba, String categorie);

    //REST
    Proba addProba(Proba proba);
    Proba deleteProba(Proba proba);
    List<Proba> getAllProba();
    Proba findByIdProba(String id_proba);
}
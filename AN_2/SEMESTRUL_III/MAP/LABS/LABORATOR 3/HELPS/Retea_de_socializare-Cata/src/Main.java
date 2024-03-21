import UI.Console;
import domain.Prietenie;
import domain.Utilizator;
import repository.PrietenieRepository;
import repository.Repository;
import repository.UtlizatorRepository;
import service.Service;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Repository<String,Utilizator> utilizatori = new UtlizatorRepository("utilizatori.txt");
        Repository<String,Prietenie> prieteni = new PrietenieRepository("prieteni.txt");
        Service srv= new Service(utilizatori,prieteni);
        Console console = new Console(srv);
        console.start();
    }
}
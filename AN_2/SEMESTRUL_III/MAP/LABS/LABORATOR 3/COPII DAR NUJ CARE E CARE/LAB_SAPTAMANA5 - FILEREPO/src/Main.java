import UI.UI;
import domain.Prietenie;
import domain.Utilizator;
import repo.PrietenieRepository;
import repo.Repository;
import repo.UtilizatorRepository;
import service.Service;

public class Main {
    public static void main(String[] args){

        Repository<String,Utilizator> utilizatorRepository = new UtilizatorRepository("C:\\Users\\andre\\Desktop\\MAP\\LABS\\LABORATOR 3\\LAB_SAPTAMANA5\\src\\utilizatori.txt");
        Repository<String,Prietenie> prietenieRepository = new PrietenieRepository("C:\\Users\\andre\\Desktop\\MAP\\LABS\\LABORATOR 3\\LAB_SAPTAMANA5\\src\\prietenii.txt");
        Service service = new Service(utilizatorRepository,prietenieRepository);
        UI ui = new UI(service);
        ui.start();
    }
}
import UI.UI;
import domain.Prietenie;
import domain.Utilizator;
import repo.PrietenieRepository;
import repo.Repository;
import repo.UtilizatorRepository;
import repo.db.PrietenieDBRepository;
import repo.db.UtilizatorDBRepository;
import service.Service;

public class Main {
    public static void main(String[] args){

            Repository<String,Utilizator> utilizatorRepository = new UtilizatorDBRepository("jdbc:postgresql://localhost:5432/UP","postgres","ubb22");
            Repository<String, Prietenie> prietenieRepository = new PrietenieDBRepository("jdbc:postgresql://localhost:5432/UP","postgres","ubb22");


            Service service = new Service(utilizatorRepository, prietenieRepository);
            UI ui = new UI(service);
            ui.start();
    }
}
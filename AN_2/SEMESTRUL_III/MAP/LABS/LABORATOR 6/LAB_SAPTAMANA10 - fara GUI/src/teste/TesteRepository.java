package teste;

import domain.Utilizator;
import org.junit.Test;
import repo.UtilizatorRepository;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class TesteRepository {
    @Test
    public void testAdaugaStergeUtilizator(){
        UtilizatorRepository repo=new UtilizatorRepository("C:\\Users\\andre\\Desktop\\MAP\\LABS\\LABORATOR 3\\LAB_SAPTAMANA5\\src\\TESTE_U.txt");

        List<Utilizator> copy=repo.getAll();

        assertTrue(repo.getAll().size()==14);

        Utilizator u1=new Utilizator("1","email1","1","Nume1","Prenume1");
        repo.adauga(u1);
        assertTrue(repo.getAll().size()==15);

        repo.sterge(u1);
        assertTrue(repo.getAll().size()==14);

        repo.saveToFile(copy);
    }

    @Test
    public void testAdaugaStergePrietenie(){

    }
}

package Teste;

import domain.Prietenie;
import domain.Utilizator;
import org.junit.Test;
import repository.PrietenieRepository;
import repository.UtlizatorRepository;

import static org.junit.Assert.assertTrue;

public class Teste_Repository {
    @Test
    public void test_adauga_Utilizator(){

        UtlizatorRepository r = new UtlizatorRepository("teste.txt");

        Utilizator u_1=new Utilizator("ID_1","lastname_1","firstname_1","e_1@gmail.com","pass_1");
        r.adauga(u_1);
        assertTrue(r.get_all().size()==1);
        assertTrue(r.get_all().get(0).equals(u_1));

        Utilizator u_2=new Utilizator("ID_2","lastname_2","firstname_2","e_2@gmail.com","pass_2");
        r.adauga(u_2);
        assertTrue(r.get_all().size()==2);
        assertTrue(r.get_all().get(1).equals(u_2));

        Utilizator u_3=new Utilizator("ID_3","lastname_3","firstname_3","e_3@gmail.com","pass_3");
        r.adauga(u_3);
        assertTrue(r.get_all().size()==3);
        assertTrue(r.get_all().get(2).equals(u_3));

        r.sterge(u_1);
        r.sterge(u_2);
        r.sterge(u_3);
    }

    @Test
    public void test_Sterge_Utilizator(){
        UtlizatorRepository r = new UtlizatorRepository("teste.txt");

        Utilizator u_1=new Utilizator("ID_1","lastname_1","firstname_1","e_1@gmail.com","pass_1");
        r.adauga(u_1);
        r.sterge(u_1);

        assertTrue(r.get_all().size()==0);

        Utilizator u_2=new Utilizator("ID_2","lastname_2","firstname_2","e_2@gmail.com","pass_2");
        r.adauga(u_1);
        r.adauga(u_2);
        assertTrue(r.sterge(u_1).equals(u_1));
        assertTrue(r.get_all().size()==1);
        assertTrue(r.sterge(u_2).equals(u_2));
        assertTrue(r.get_all().size()==0);
    }

    @Test
    public void test_Cauta_Dupa_Id_Utilizator(){
        UtlizatorRepository r = new UtlizatorRepository("teste.txt");
        Utilizator u_1=new Utilizator("ID_1","lastname_1","firstname_1","e_1@gmail.com","pass_1");
        Utilizator u_2=new Utilizator("ID_2","lastname_2","firstname_2","e_2@gmail.com","pass_2");
        Utilizator u_3=new Utilizator("ID_3","lastname_3","firstname_3","e_3@gmail.com","pass_3");
        Utilizator u_4=new Utilizator("ID_4","lastname_4","firstname_4","e_4@gmail.com","pass_4");

        r.adauga(u_1);
        r.adauga(u_2);
        r.adauga(u_3);
        r.adauga(u_4);

        assertTrue(r.cauta_dupa_id("ID_1").equals(u_1));
        assertTrue(r.cauta_dupa_id("ID_2").equals(u_2));
        assertTrue(r.cauta_dupa_id("ID_3").equals(u_3));
        assertTrue(r.cauta_dupa_id("ID_4").equals(u_4));

        r.sterge(u_1);
        r.sterge(u_2);
        r.sterge(u_3);
        r.sterge(u_4);
    }

    @Test
    public void test_adauga_Prietenie(){

        PrietenieRepository r = new PrietenieRepository("teste.txt");

        Prietenie p_1=new Prietenie("ID_1","prieten_1","prieten_2");
        r.adauga(p_1);
        assertTrue(r.get_all().size()==1);
        assertTrue(r.get_all().get(0).equals(p_1));

        Prietenie p_2=new Prietenie("ID_2","prieten_3","prieten_4");
        r.adauga(p_2);
        assertTrue(r.get_all().size()==2);
        assertTrue(r.get_all().get(1).equals(p_2));

        Prietenie p_3=new Prietenie("ID_3","prieten_5","prieten_6");
        r.adauga(p_3);
        assertTrue(r.get_all().size()==3);
        assertTrue(r.get_all().get(2).equals(p_3));

        r.sterge(p_1);
        r.sterge(p_2);
        r.sterge(p_3);
    }

    @Test
    public void test_Sterge_Prietenie(){
        PrietenieRepository r = new PrietenieRepository("teste.txt");

        Prietenie p_1=new Prietenie("ID_1","prieten_1","prieten_2");
        r.adauga(p_1);
        assertTrue(r.sterge(p_1).equals(p_1));
        assertTrue(r.get_all().size()==0);

        Prietenie p_2=new Prietenie("ID_2","prieten_2","prieten_5");
        Prietenie p_3=new Prietenie("ID_3","prieten_3","prieten_4");
        r.adauga(p_1);
        r.adauga(p_2);
        r.adauga(p_3);

        assertTrue(r.sterge(p_2).equals(p_2));
        assertTrue(r.get_all().size()==2);
        assertTrue(r.sterge(p_1).equals(p_1));
        assertTrue(r.get_all().size()==1);
        assertTrue(r.sterge(p_3).equals(p_3));
        assertTrue(r.get_all().size()==0);
    }
    @Test
    public void test_Cauta_Dupa_Id_Prietenie(){
        PrietenieRepository r = new PrietenieRepository("teste.txt");

        Prietenie p_1=new Prietenie("ID_1","prieten_1","prieten_5");
        Prietenie p_2=new Prietenie("ID_2","prieten_2","prieten_6");
        Prietenie p_3=new Prietenie("ID_3","prieten_3","prieten_7");
        Prietenie p_4=new Prietenie("ID_4","prieten_4","prieten_8");
        r.adauga(p_1);
        r.adauga(p_2);
        r.adauga(p_3);
        r.adauga(p_4);

        assertTrue(r.cauta_dupa_id("ID_1").equals(p_1));
        assertTrue(r.cauta_dupa_id("ID_2").equals(p_2));
        assertTrue(r.cauta_dupa_id("ID_3").equals(p_3));
        assertTrue(r.cauta_dupa_id("ID_4").equals(p_4));

        r.sterge(p_1);
        r.sterge(p_2);
        r.sterge(p_3);
        r.sterge(p_4);
    }
}

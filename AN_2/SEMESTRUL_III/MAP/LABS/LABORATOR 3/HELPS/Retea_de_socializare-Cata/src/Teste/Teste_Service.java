package Teste;

import domain.Prietenie;
import domain.Utilizator;
import validators.ValidationException;
import org.junit.Test;
import repository.PrietenieRepository;
import repository.Repository;
import repository.UtlizatorRepository;
import service.Service;

import static org.junit.Assert.assertTrue;

public class Teste_Service {
    @Test
    public void test_adaugare_Utilizator(){
        Repository<String,Utilizator> utilizatori = new UtlizatorRepository("teste.txt");
        Repository<String,Prietenie> prieteni = new PrietenieRepository("teste_prieteni.txt");
        Service srv= new Service(utilizatori,prieteni);

        srv.adaugaUtilizator("nume_1","prenume_1","c.b@gmail.com","pass_2");
        srv.adaugaUtilizator("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.adaugaUtilizator("nume_3","prenume_3","c.b_3@gmail.com","pass_4");
        assertTrue(srv.get_all_Utilizatori().size()==3);
        assertTrue(srv.get_all_Utilizatori().get(0).get_email().equals("c.b@gmail.com"));
        assertTrue(srv.get_all_Utilizatori().get(1).get_email().equals("c.b_2@gmail.com"));
        assertTrue(srv.get_all_Utilizatori().get(2).get_email().equals("c.b_3@gmail.com"));

        try{
            srv.adaugaUtilizator("nume_1","prenume_1","c.b@gmail.com","pass_2");
            assertTrue(false);
        }
        catch(ValidationException e){
            assertTrue(true);
        }

        try{
            srv.adaugaUtilizator("","","lol@gmail.com","pass_5");
            assertTrue(false);
        }catch(ValidationException e){
            assertTrue(true);
        }

        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
    }

    @Test
    public void test_sterge_Utilizator_dupa_id(){
        Repository<String,Utilizator> utilizatori = new UtlizatorRepository("teste.txt");
        Repository<String,Prietenie> prieteni = new PrietenieRepository("teste_prieteni.txt");
        Service srv= new Service(utilizatori,prieteni);

        srv.adaugaUtilizator("nume_1","prenume_1","c.b@gmail.com","pass_2");
        srv.adaugaUtilizator("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.adaugaUtilizator("nume_3","prenume_3","c.b_3@gmail.com","pass_4");

        try{
            srv.stergeUtilizator_dupa_id("nu_exista");
            assertTrue(false);
        }
        catch(ValidationException e){
            assertTrue(true);
        }

        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        assertTrue(srv.get_all_Utilizatori().size()==2);
        assertTrue(srv.get_all_Utilizatori().get(0).get_email().equals("c.b_2@gmail.com"));
        assertTrue(srv.get_all_Utilizatori().get(1).get_email().equals("c.b_3@gmail.com"));

        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        assertTrue(srv.get_all_Utilizatori().size()==1);
        assertTrue(srv.get_all_Utilizatori().get(0).get_email().equals("c.b_3@gmail.com"));

        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        assertTrue(srv.get_all_Utilizatori().size()==0);
    }

    @Test
    public void test_sterge_Utilizator_email_pass(){
        Repository<String,Utilizator> utilizatori = new UtlizatorRepository("teste.txt");
        Repository<String,Prietenie> prieteni = new PrietenieRepository("teste_prieteni.txt");
        Service srv= new Service(utilizatori,prieteni);

        srv.adaugaUtilizator("nume_1","prenume_1","c.b@gmail.com","pass_2");
        srv.adaugaUtilizator("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.adaugaUtilizator("nume_3","prenume_3","c.b_3@gmail.com","pass_4");

        try{
            srv.stergeUtilizator("nu_exista_1","nu_exista_2");
            assertTrue(false);
        }catch(ValidationException e){
            assertTrue(true);
        }

        srv.stergeUtilizator("c.b@gmail.com","pass_2");
        assertTrue(srv.get_all_Utilizatori().size()==2);
        assertTrue(srv.get_all_Utilizatori().get(0).get_email().equals("c.b_2@gmail.com"));
        assertTrue(srv.get_all_Utilizatori().get(1).get_email().equals("c.b_3@gmail.com"));

        srv.stergeUtilizator("c.b_2@gmail.com","pass_3");
        assertTrue(srv.get_all_Utilizatori().size()==1);
        assertTrue(srv.get_all_Utilizatori().get(0).get_email().equals("c.b_3@gmail.com"));

        srv.stergeUtilizator("c.b_3@gmail.com","pass_4");
        assertTrue(srv.get_all_Utilizatori().size()==0);
    }

    @Test
    public void test_adauga_Prietenie_id(){
        Repository<String,Utilizator> utilizatori = new UtlizatorRepository("teste.txt");
        Repository<String,Prietenie> prieteni = new PrietenieRepository("teste_prieteni.txt");
        Service srv= new Service(utilizatori,prieteni);

        srv.adaugaUtilizator("nume_1","prenume_1","c.b_1@gmail.com","pass_2");
        srv.adaugaUtilizator("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.adaugaUtilizator("nume_3","prenume_3","c.b_3@gmail.com","pass_4");
        srv.adaugaUtilizator("nume_4","prenume_4","c.b_4@gmail.com","pass_5");

        srv.adaugaPrietenie_dupa_id(srv.get_all_Utilizatori().get(0).get_id(),srv.get_all_Utilizatori().get(1).get_id());
        assertTrue(srv.get_all_Prietenii().size()==1);
        assertTrue(srv.get_all_Prietenii().get(0).get_prieten_1().equals(srv.get_all_Utilizatori().get(0).get_id()));
        assertTrue(srv.get_all_Prietenii().get(0).get_prieten_2().equals(srv.get_all_Utilizatori().get(1).get_id()));

        try{
            srv.adaugaPrietenie_dupa_id(srv.get_all_Utilizatori().get(0).get_id(),srv.get_all_Utilizatori().get(1).get_id());
            assertTrue(false);
        }catch(ValidationException e){
            assertTrue(true);
        }

        srv.adaugaPrietenie_dupa_id(srv.get_all_Utilizatori().get(2).get_id(),srv.get_all_Utilizatori().get(3).get_id());
        assertTrue(srv.get_all_Prietenii().size()==2);
        assertTrue(srv.get_all_Prietenii().get(1).get_prieten_1().equals(srv.get_all_Utilizatori().get(2).get_id()));
        assertTrue(srv.get_all_Prietenii().get(1).get_prieten_2().equals(srv.get_all_Utilizatori().get(3).get_id()));

        srv.adaugaPrietenie_dupa_id(srv.get_all_Utilizatori().get(1).get_id(),srv.get_all_Utilizatori().get(3).get_id());
        assertTrue(srv.get_all_Prietenii().size()==3);
        assertTrue(srv.get_all_Prietenii().get(2).get_prieten_1().equals(srv.get_all_Utilizatori().get(1).get_id()));
        assertTrue(srv.get_all_Prietenii().get(2).get_prieten_2().equals(srv.get_all_Utilizatori().get(3).get_id()));

        srv.adaugaPrietenie_dupa_id(srv.get_all_Utilizatori().get(0).get_id(),srv.get_all_Utilizatori().get(3).get_id());
        assertTrue(srv.get_all_Prietenii().size()==4);
        assertTrue(srv.get_all_Prietenii().get(3).get_prieten_1().equals(srv.get_all_Utilizatori().get(0).get_id()));
        assertTrue(srv.get_all_Prietenii().get(3).get_prieten_2().equals(srv.get_all_Utilizatori().get(3).get_id()));


        srv.stergePrietenie_dupa_id(srv.get_all_Utilizatori().get(0).get_id(),srv.get_all_Utilizatori().get(1).get_id());
        srv.stergePrietenie_dupa_id(srv.get_all_Utilizatori().get(2).get_id(),srv.get_all_Utilizatori().get(3).get_id());
        srv.stergePrietenie_dupa_id(srv.get_all_Utilizatori().get(1).get_id(),srv.get_all_Utilizatori().get(3).get_id());
        srv.stergePrietenie_dupa_id(srv.get_all_Utilizatori().get(0).get_id(),srv.get_all_Utilizatori().get(3).get_id());

        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
    }

    @Test
    public void test_adauga_Prietenie_email(){
        Repository<String,Utilizator> utilizatori = new UtlizatorRepository("teste.txt");
        Repository<String,Prietenie> prieteni = new PrietenieRepository("teste_prieteni.txt");
        Service srv= new Service(utilizatori,prieteni);

        srv.adaugaUtilizator("nume_1","prenume_1","c.b_1@gmail.com","pass_2");
        srv.adaugaUtilizator("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.adaugaUtilizator("nume_3","prenume_3","c.b_3@gmail.com","pass_4");
        srv.adaugaUtilizator("nume_4","prenume_4","c.b_4@gmail.com","pass_5");

        srv.adaugaPrietenie(srv.get_all_Utilizatori().get(0).get_email(),srv.get_all_Utilizatori().get(1).get_email());
        assertTrue(srv.get_all_Prietenii().size()==1);
        assertTrue(srv.get_all_Prietenii().get(0).get_prieten_1().equals(srv.get_all_Utilizatori().get(0).get_id()));
        assertTrue(srv.get_all_Prietenii().get(0).get_prieten_2().equals(srv.get_all_Utilizatori().get(1).get_id()));

        try{
            srv.adaugaPrietenie(srv.get_all_Utilizatori().get(0).get_email(),srv.get_all_Utilizatori().get(1).get_email());
            assertTrue(false);
        }catch(ValidationException e){
            assertTrue(true);
        }

        srv.adaugaPrietenie(srv.get_all_Utilizatori().get(2).get_email(),srv.get_all_Utilizatori().get(3).get_email());
        assertTrue(srv.get_all_Prietenii().size()==2);
        assertTrue(srv.get_all_Prietenii().get(1).get_prieten_1().equals(srv.get_all_Utilizatori().get(2).get_id()));
        assertTrue(srv.get_all_Prietenii().get(1).get_prieten_2().equals(srv.get_all_Utilizatori().get(3).get_id()));

        srv.adaugaPrietenie(srv.get_all_Utilizatori().get(1).get_email(),srv.get_all_Utilizatori().get(3).get_email());
        assertTrue(srv.get_all_Prietenii().size()==3);
        assertTrue(srv.get_all_Prietenii().get(2).get_prieten_1().equals(srv.get_all_Utilizatori().get(1).get_id()));
        assertTrue(srv.get_all_Prietenii().get(2).get_prieten_2().equals(srv.get_all_Utilizatori().get(3).get_id()));

        srv.adaugaPrietenie(srv.get_all_Utilizatori().get(0).get_email(),srv.get_all_Utilizatori().get(3).get_email());
        assertTrue(srv.get_all_Prietenii().size()==4);
        assertTrue(srv.get_all_Prietenii().get(3).get_prieten_1().equals(srv.get_all_Utilizatori().get(0).get_id()));
        assertTrue(srv.get_all_Prietenii().get(3).get_prieten_2().equals(srv.get_all_Utilizatori().get(3).get_id()));


        srv.stergePrietenie_dupa_id(srv.get_all_Utilizatori().get(0).get_id(),srv.get_all_Utilizatori().get(1).get_id());
        srv.stergePrietenie_dupa_id(srv.get_all_Utilizatori().get(2).get_id(),srv.get_all_Utilizatori().get(3).get_id());
        srv.stergePrietenie_dupa_id(srv.get_all_Utilizatori().get(1).get_id(),srv.get_all_Utilizatori().get(3).get_id());
        srv.stergePrietenie_dupa_id(srv.get_all_Utilizatori().get(0).get_id(),srv.get_all_Utilizatori().get(3).get_id());

        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
    }

    @Test
    public void test_sterge_Prietenie_id(){
        Repository<String,Utilizator> utilizatori = new UtlizatorRepository("teste.txt");
        Repository<String,Prietenie> prieteni = new PrietenieRepository("teste_prieteni.txt");
        Service srv= new Service(utilizatori,prieteni);

        srv.adaugaUtilizator("nume_1","prenume_1","c.b_1@gmail.com","pass_2");
        srv.adaugaUtilizator("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.adaugaUtilizator("nume_3","prenume_3","c.b_3@gmail.com","pass_4");
        srv.adaugaUtilizator("nume_4","prenume_4","c.b_4@gmail.com","pass_5");

        srv.adaugaPrietenie(srv.get_all_Utilizatori().get(0).get_email(),srv.get_all_Utilizatori().get(1).get_email());
        srv.adaugaPrietenie(srv.get_all_Utilizatori().get(2).get_email(),srv.get_all_Utilizatori().get(3).get_email());
        srv.adaugaPrietenie(srv.get_all_Utilizatori().get(1).get_email(),srv.get_all_Utilizatori().get(3).get_email());
        srv.adaugaPrietenie(srv.get_all_Utilizatori().get(0).get_email(),srv.get_all_Utilizatori().get(3).get_email());

        try{
            srv.stergePrietenie_dupa_id("nu_exista_1","nu_exista_2");
            assertTrue(false);
        }catch (ValidationException e){
            assertTrue(true);
        }

        srv.stergePrietenie_dupa_id(srv.get_all_Utilizatori().get(0).get_id(),srv.get_all_Utilizatori().get(1).get_id());
        assertTrue(srv.get_all_Prietenii().size()==3);
        assertTrue(srv.get_all_Prietenii().get(0).get_prieten_1().equals(srv.get_all_Utilizatori().get(2).get_id()));
        assertTrue(srv.get_all_Prietenii().get(0).get_prieten_2().equals(srv.get_all_Utilizatori().get(3).get_id()));

        srv.stergePrietenie_dupa_id(srv.get_all_Utilizatori().get(2).get_id(),srv.get_all_Utilizatori().get(3).get_id());
        assertTrue(srv.get_all_Prietenii().size()==2);
        assertTrue(srv.get_all_Prietenii().get(0).get_prieten_1().equals(srv.get_all_Utilizatori().get(1).get_id()));
        assertTrue(srv.get_all_Prietenii().get(0).get_prieten_2().equals(srv.get_all_Utilizatori().get(3).get_id()));

        srv.stergePrietenie_dupa_id(srv.get_all_Utilizatori().get(1).get_id(),srv.get_all_Utilizatori().get(3).get_id());
        assertTrue(srv.get_all_Prietenii().size()==1);
        assertTrue(srv.get_all_Prietenii().get(0).get_prieten_1().equals(srv.get_all_Utilizatori().get(0).get_id()));
        assertTrue(srv.get_all_Prietenii().get(0).get_prieten_2().equals(srv.get_all_Utilizatori().get(3).get_id()));

        srv.stergePrietenie_dupa_id(srv.get_all_Utilizatori().get(0).get_id(),srv.get_all_Utilizatori().get(3).get_id());
        assertTrue(srv.get_all_Prietenii().size()==0);

        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
    }

    @Test
    public void test_sterge_Prietenie_email(){
        Repository<String,Utilizator> utilizatori = new UtlizatorRepository("teste.txt");
        Repository<String,Prietenie> prieteni = new PrietenieRepository("teste_prieteni.txt");
        Service srv= new Service(utilizatori,prieteni);

        srv.adaugaUtilizator("nume_1","prenume_1","c.b_1@gmail.com","pass_2");
        srv.adaugaUtilizator("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.adaugaUtilizator("nume_3","prenume_3","c.b_3@gmail.com","pass_4");
        srv.adaugaUtilizator("nume_4","prenume_4","c.b_4@gmail.com","pass_5");

        srv.adaugaPrietenie(srv.get_all_Utilizatori().get(0).get_email(),srv.get_all_Utilizatori().get(1).get_email());
        srv.adaugaPrietenie(srv.get_all_Utilizatori().get(2).get_email(),srv.get_all_Utilizatori().get(3).get_email());
        srv.adaugaPrietenie(srv.get_all_Utilizatori().get(1).get_email(),srv.get_all_Utilizatori().get(3).get_email());
        srv.adaugaPrietenie(srv.get_all_Utilizatori().get(0).get_email(),srv.get_all_Utilizatori().get(3).get_email());

        try{
            srv.stergePrietenie("nu_exista_1","nu_exista_2");
            assertTrue(false);
        }catch (ValidationException e){
            assertTrue(true);
        }

        srv.stergePrietenie(srv.get_all_Utilizatori().get(0).get_email(),srv.get_all_Utilizatori().get(1).get_email());
        assertTrue(srv.get_all_Prietenii().size()==3);
        assertTrue(srv.get_all_Prietenii().get(0).get_prieten_1().equals(srv.get_all_Utilizatori().get(2).get_id()));
        assertTrue(srv.get_all_Prietenii().get(0).get_prieten_2().equals(srv.get_all_Utilizatori().get(3).get_id()));

        srv.stergePrietenie(srv.get_all_Utilizatori().get(2).get_email(),srv.get_all_Utilizatori().get(3).get_email());
        assertTrue(srv.get_all_Prietenii().size()==2);
        assertTrue(srv.get_all_Prietenii().get(0).get_prieten_1().equals(srv.get_all_Utilizatori().get(1).get_id()));
        assertTrue(srv.get_all_Prietenii().get(0).get_prieten_2().equals(srv.get_all_Utilizatori().get(3).get_id()));

        srv.stergePrietenie(srv.get_all_Utilizatori().get(1).get_email(),srv.get_all_Utilizatori().get(3).get_email());
        assertTrue(srv.get_all_Prietenii().size()==1);
        assertTrue(srv.get_all_Prietenii().get(0).get_prieten_1().equals(srv.get_all_Utilizatori().get(0).get_id()));
        assertTrue(srv.get_all_Prietenii().get(0).get_prieten_2().equals(srv.get_all_Utilizatori().get(3).get_id()));

        srv.stergePrietenie(srv.get_all_Utilizatori().get(0).get_email(),srv.get_all_Utilizatori().get(3).get_email());
        assertTrue(srv.get_all_Prietenii().size()==0);

        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
    }

    @Test
    public void test_numar_componente_conexe(){
        Repository<String,Utilizator> utilizatori = new UtlizatorRepository("teste.txt");
        Repository<String,Prietenie> prieteni = new PrietenieRepository("teste_prieteni.txt");
        Service srv= new Service(utilizatori,prieteni);

        srv.adaugaUtilizator("nume_1","prenume_1","c.b_1@gmail.com","pass_2");
        srv.adaugaUtilizator("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.adaugaUtilizator("nume_3","prenume_3","c.b_3@gmail.com","pass_4");
        srv.adaugaUtilizator("nume_4","prenume_4","c.b_4@gmail.com","pass_5");
        srv.adaugaUtilizator("nume_4","prenume_4","c.b_5@gmail.com","pass_5");
        srv.adaugaUtilizator("nume_4","prenume_4","c.b_6@gmail.com","pass_5");

        srv.adaugaPrietenie("c.b_1@gmail.com","c.b_2@gmail.com");
        assertTrue(srv.get_numar_componente_conexe().equals(1));

        srv.adaugaPrietenie("c.b_3@gmail.com","c.b_4@gmail.com");
        assertTrue(srv.get_numar_componente_conexe().equals(2));

        srv.adaugaPrietenie("c.b_1@gmail.com","c.b_4@gmail.com");
        assertTrue(srv.get_numar_componente_conexe().equals(1));

        srv.adaugaPrietenie("c.b_5@gmail.com","c.b_6@gmail.com");
        assertTrue(srv.get_numar_componente_conexe().equals(2));

        srv.stergePrietenie("c.b_1@gmail.com","c.b_4@gmail.com");
        assertTrue(srv.get_numar_componente_conexe().equals(3));

        srv.stergePrietenie_dupa_id(srv.get_all_Prietenii().get(0).get_prieten_1(),srv.get_all_Prietenii().get(0).get_prieten_2());
        srv.stergePrietenie_dupa_id(srv.get_all_Prietenii().get(0).get_prieten_1(),srv.get_all_Prietenii().get(0).get_prieten_2());
        srv.stergePrietenie_dupa_id(srv.get_all_Prietenii().get(0).get_prieten_1(),srv.get_all_Prietenii().get(0).get_prieten_2());

        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
    }

    @Test
    public void test_numar_comp_conecta(){
        Repository<String,Utilizator> utilizatori = new UtlizatorRepository("teste.txt");
        Repository<String,Prietenie> prieteni = new PrietenieRepository("teste_prieteni.txt");
        Service srv= new Service(utilizatori,prieteni);

        srv.adaugaUtilizator("nume_1","prenume_1","c.b_1@gmail.com","pass_2");
        srv.adaugaUtilizator("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.adaugaUtilizator("nume_3","prenume_3","c.b_3@gmail.com","pass_4");
        srv.adaugaUtilizator("nume_4","prenume_4","c.b_4@gmail.com","pass_5");
        srv.adaugaUtilizator("nume_4","prenume_4","c.b_5@gmail.com","pass_5");
        srv.adaugaUtilizator("nume_4","prenume_4","c.b_6@gmail.com","pass_5");

        srv.adaugaPrietenie("c.b_1@gmail.com","c.b_2@gmail.com");
        assertTrue(srv.get_comp_conecta().size()==2);

        srv.adaugaPrietenie("c.b_3@gmail.com","c.b_4@gmail.com");
        assertTrue(srv.get_comp_conecta().size()==2);

        srv.adaugaPrietenie("c.b_1@gmail.com","c.b_4@gmail.com");
        assertTrue(srv.get_comp_conecta().size()==4);

        srv.adaugaPrietenie("c.b_5@gmail.com","c.b_6@gmail.com");
        assertTrue(srv.get_comp_conecta().size()==4);

        srv.adaugaPrietenie("c.b_1@gmail.com","c.b_6@gmail.com");
        assertTrue(srv.get_comp_conecta().size()==6);

        srv.stergePrietenie_dupa_id(srv.get_all_Prietenii().get(0).get_prieten_1(),srv.get_all_Prietenii().get(0).get_prieten_2());
        srv.stergePrietenie_dupa_id(srv.get_all_Prietenii().get(0).get_prieten_1(),srv.get_all_Prietenii().get(0).get_prieten_2());
        srv.stergePrietenie_dupa_id(srv.get_all_Prietenii().get(0).get_prieten_1(),srv.get_all_Prietenii().get(0).get_prieten_2());
        srv.stergePrietenie_dupa_id(srv.get_all_Prietenii().get(0).get_prieten_1(),srv.get_all_Prietenii().get(0).get_prieten_2());
        srv.stergePrietenie_dupa_id(srv.get_all_Prietenii().get(0).get_prieten_1(),srv.get_all_Prietenii().get(0).get_prieten_2());

        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
        srv.stergeUtilizator_dupa_id(srv.get_all_Utilizatori().get(0).get_id());
    }


}

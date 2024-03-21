package Teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import domain.Prietenie;
import domain.Utilizator;
import org.junit.Test;


public class Teste_Domain {

    @Test
    public void test_create_Utilizator(){
        Utilizator u=new Utilizator("ID_1","FirstName_1","LastName_2","email_1@gmail.com","passwd_1");
        assertTrue(u.get_id().equals("ID_1"));
        assertTrue(u.get_firstname().equals("FirstName_1"));
        assertTrue(u.get_lastname().equals("LastName_2"));
        assertTrue(u.get_email().equals("email_1@gmail.com"));
        assertTrue(u.get_parola().equals("passwd_1"));

        Utilizator u_2=new Utilizator("ID_2","FirstName_2","LastName_3","email_2@gmail.com","passwd_2");
        assertTrue(u_2.get_id().equals("ID_2"));
        assertTrue(u_2.get_firstname().equals("FirstName_2"));
        assertTrue(u_2.get_lastname().equals("LastName_3"));
        assertTrue(u_2.get_email().equals("email_2@gmail.com"));
        assertTrue(u_2.get_parola().equals("passwd_2"));

        Utilizator u_3=new Utilizator("ID_3","FirstName_3","LastName_4","email_3@gmail.com","passwd_3");
        assertTrue(u_3.get_id().equals("ID_3"));
        assertTrue(u_3.get_firstname().equals("FirstName_3"));
        assertTrue(u_3.get_lastname().equals("LastName_4"));
        assertTrue(u_3.get_email().equals("email_3@gmail.com"));
        assertTrue(u_3.get_parola().equals("passwd_3"));
    }

    @Test
    public void test_set_Utilizator(){
        Utilizator u=new Utilizator("ID_1","FirstName_1","LastName_2","e_1@g.com","pass_1");
        u.set_id("new_id_1");
        u.set_firstname("new_firstname_1");
        u.set_lastname("new_lastname_1");
        u.set_email("new_e_1@g.com");
        u.set_parola("new_pass_1");
        assertTrue(u.get_id().equals("new_id_1"));
        assertTrue(u.get_firstname().equals("new_firstname_1"));
        assertTrue(u.get_lastname().equals("new_lastname_1"));
        assertTrue(u.get_email().equals("new_e_1@g.com"));
        assertTrue(u.get_parola().equals("new_pass_1"));

        Utilizator u_2=new Utilizator("ID_1","FirstName_1","LastName_2","e_1@g.com","pass_1");
        u_2.set_id("new_id_2");
        u_2.set_firstname("new_firstname_2");
        u_2.set_lastname("new_lastname_2");
        u_2.set_email("new_e_2@g.com");
        u_2.set_parola("new_pass_2");
        assertTrue(u_2.get_id().equals("new_id_2"));
        assertTrue(u_2.get_firstname().equals("new_firstname_2"));
        assertTrue(u_2.get_lastname().equals("new_lastname_2"));
        assertTrue(u_2.get_email().equals("new_e_2@g.com"));
        assertTrue(u_2.get_parola().equals("new_pass_2"));

        Utilizator u_3=new Utilizator("ID_2","FirstName_2","LastName_2","e_1@g.com","pass_1");
        u_3.set_id("new_id_3");
        u_3.set_firstname("new_firstname_3");
        u_3.set_lastname("new_lastname_3");
        u_3.set_email("new_e_3@g.com");
        u_3.set_parola("new_pass_3");
        assertTrue(u_3.get_id().equals("new_id_3"));
        assertTrue(u_3.get_firstname().equals("new_firstname_3"));
        assertTrue(u_3.get_lastname().equals("new_lastname_3"));
        assertTrue(u_3.get_email().equals("new_e_3@g.com"));
        assertTrue(u_3.get_parola().equals("new_pass_3"));
    }

    @Test
    public void test_equal_Utilizator(){
        Utilizator u_1=new Utilizator("ID_1","FirstName_1","LastName_1","e_1@g.com","pass_1");
        Utilizator u_2=new Utilizator("ID_2","FirstName_2","LastName_2","e_1@g.com","pass_1");
        assertTrue(!u_1.equals(u_2));
        Utilizator u_3=new Utilizator("ID_2","FirstName_2","LastName_2","e_1@g.com","pass_1");
        assertTrue(u_2.equals(u_3));
        assertTrue(!u_3.equals(u_1));
    }

    @Test
    public void test_create_Prietenie(){
        Prietenie p_1=new Prietenie("ID_1","Prieten_1","Prieten_2");
        assertTrue(p_1.get_id().equals("ID_1"));
        assertTrue(p_1.get_prieten_1().equals("Prieten_1"));
        assertTrue(p_1.get_prieten_2().equals("Prieten_2"));

        Prietenie p_2=new Prietenie("ID_2","Prieten_3","Prieten_4");
        assertTrue(p_2.get_id().equals("ID_2"));
        assertTrue(p_2.get_prieten_1().equals("Prieten_3"));
        assertTrue(p_2.get_prieten_2().equals("Prieten_4"));

        Prietenie p_3=new Prietenie("ID_3","Prieten_5","Prieten_6");
        assertTrue(p_3.get_id().equals("ID_3"));
        assertTrue(p_3.get_prieten_1().equals("Prieten_5"));
        assertTrue(p_3.get_prieten_2().equals("Prieten_6"));
    }

    @Test
    public void test_equal_Prietenie(){
        Prietenie p_1=new Prietenie("ID_1","Prieten_1","Prieten_2");
        Prietenie p_2=new Prietenie("ID_1","Prieten_1","Prieten_2");
        Prietenie p_3=new Prietenie("ID_3","Prieten_5","Prieten_6");
        assertTrue(p_1.equals(p_2));
        assertTrue(!p_2.equals(p_3));
        assertTrue(!p_1.equals(p_3));
    }
}

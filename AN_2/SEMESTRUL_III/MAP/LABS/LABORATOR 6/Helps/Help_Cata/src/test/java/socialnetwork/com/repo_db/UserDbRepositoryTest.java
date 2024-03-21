package socialnetwork.com.repo_db;

import org.junit.jupiter.api.Test;
import socialnetwork.com.domain.User;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDbRepositoryTest {

    @Test
    void adauga() {
        UserDbRepository r = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","users_test");

        User u_1=new User(1,"lastname_1","firstname_1","e_1@gmail.com","pass_1");
        r.add(u_1);
        assertTrue(r.getAll().size()==1);
        assertTrue(r.getAll().get(0).equals(u_1));

        User u_2=new User(2,"lastname_2","firstname_2","e_2@gmail.com","pass_2");
        r.add(u_2);
        assertTrue(r.getAll().size()==2);
        assertTrue(r.getAll().get(1).equals(u_2));

        User u_3=new User(3,"lastname_3","firstname_3","e_3@gmail.com","pass_3");
        r.add(u_3);
        assertTrue(r.getAll().size()==3);
        assertTrue(r.getAll().get(2).equals(u_3));

        r.delete(u_1);
        r.delete(u_2);
        r.delete(u_3);
    }

    @Test
    void sterge() {
        UserDbRepository r = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","users_test");

        User u_1=new User(1,"lastname_1","firstname_1","e_1@gmail.com","pass_1");
        r.add(u_1);
        r.delete(u_1);

        assertTrue(r.getAll().size()==0);

        User u_2=new User(2,"lastname_2","firstname_2","e_2@gmail.com","pass_2");
        r.add(u_1);

        r.add(u_2);
        assertTrue(r.delete(u_1).equals(u_1));
        assertTrue(r.getAll().size()==1);
        assertTrue(r.delete(u_2).equals(u_2));
        assertTrue(r.getAll().size()==0);
    }

    @Test
    void cauta_dupa_id() {
        UserDbRepository r = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","users_test");
        User u_1=new User(1,"lastname_1","firstname_1","e_1@gmail.com","pass_1");
        User u_2=new User(2,"lastname_2","firstname_2","e_2@gmail.com","pass_2");
        User u_3=new User(3,"lastname_3","firstname_3","e_3@gmail.com","pass_3");
        User u_4=new User(4,"lastname_4","firstname_4","e_4@gmail.com","pass_4");

        r.add(u_1);
        r.add(u_2);
        r.add(u_3);
        r.add(u_4);

        assertTrue(r.findById(u_1.get_id()).equals(u_1));
        assertTrue(r.findById(u_2.get_id()).equals(u_2));
        assertTrue(r.findById(u_3.get_id()).equals(u_3));
        assertTrue(r.findById(u_4.get_id()).equals(u_4));

        r.delete(u_1);
        r.delete(u_2);
        r.delete(u_3);
        r.delete(u_4);
    }

    @Test
    void update() {
        UserDbRepository r = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","users_test");

        User u_1=new User(1,"lastname_1","firstname_1","e_1@gmail.com","pass_1");
        User n_1=new User(1,"lastname_1_2","firstname_1_2","e_1@gmail.com_2","pass_1_2");
        r.add(u_1);
        r.update(u_1,n_1);
        assertTrue(r.getAll().size()==1);
        assertTrue(r.getAll().get(0).equals(n_1));

        User u_2=new User(1,"lastname_2","firstname_2","e_2@gmail.com","pass_2");
        User n_2=new User(1,"lastname_2_2","firstname_2_2","e_2@gmail.com_2","pass_2_2");
        r.add(u_2);
        r.update(u_2,n_2);
        assertTrue(r.getAll().size()==2);
        assertTrue(r.getAll().get(1).equals(n_2));

        User u_3=new User(1,"lastname_3","firstname_3","e_3@gmail.com","pass_3");
        User n_3=new User(1,"lastname_3_2","firstname_3_2","e_3@gmail.com_2","pass_3_2");
        r.add(u_3);
        r.update(u_3,n_3);
        assertTrue(r.getAll().size()==3);
        assertTrue(r.getAll().get(2).equals(n_3));

        r.delete(n_1);
        r.delete(n_2);
        r.delete(n_3);
    }
}
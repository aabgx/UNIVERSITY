package socialnetwork.com.service;

import org.junit.jupiter.api.Test;
import socialnetwork.com.domain.User;
import socialnetwork.com.repo_db.FriendDbRepository;
import socialnetwork.com.repo_db.UserDbRepository;
import socialnetwork.com.validators.ValidationException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ServiceTest {

    @Test
    void adaugaUtilizator() {
        UserDbRepository utilizatori = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","users_test");

        FriendDbRepository prieteni = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","prietenie_test");

        Service srv= new Service(utilizatori,prieteni);

        srv.addUser("nume_1","prenume_1","c.b@gmail.com","pass_2");
        srv.addUser("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.addUser("nume_3","prenume_3","c.b_3@gmail.com","pass_4");
        assertTrue(srv.getAllUsers().size()==3);
        assertTrue(srv.getAllUsers().get(0).getEmail().equals("c.b@gmail.com"));
        assertTrue(srv.getAllUsers().get(1).getEmail().equals("c.b_2@gmail.com"));
        assertTrue(srv.getAllUsers().get(2).getEmail().equals("c.b_3@gmail.com"));

        try{
            srv.addUser("nume_1","prenume_1","c.b@gmail.com","pass_2");
            assertTrue(false);
        }
        catch(ValidationException e){
            assertTrue(true);
        }

        try{
            srv.addUser("","","lol@gmail.com","pass_5");
            assertTrue(false);
        }catch(ValidationException e){
            assertTrue(true);
        }

        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
    }

    @Test
    void stergeUtilizator() {
        UserDbRepository utilizatori = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","users_test");

        FriendDbRepository prieteni = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","prietenie_test");

        Service srv= new Service(utilizatori,prieteni);

        srv.addUser("nume_1","prenume_1","c.b@gmail.com","pass_2");
        srv.addUser("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.addUser("nume_3","prenume_3","c.b_3@gmail.com","pass_4");

        try{
            srv.deleteUser("nu_exista_1","nu_exista_2");
            assertTrue(false);
        }catch(ValidationException e){
            assertTrue(true);
        }

        srv.deleteUser("c.b@gmail.com","pass_2");
        assertTrue(srv.getAllUsers().size()==2);
        assertTrue(srv.getAllUsers().get(0).getEmail().equals("c.b_2@gmail.com"));
        assertTrue(srv.getAllUsers().get(1).getEmail().equals("c.b_3@gmail.com"));

        srv.deleteUser("c.b_2@gmail.com","pass_3");
        assertTrue(srv.getAllUsers().size()==1);
        assertTrue(srv.getAllUsers().get(0).getEmail().equals("c.b_3@gmail.com"));

        srv.deleteUser("c.b_3@gmail.com","pass_4");
        assertTrue(srv.getAllUsers().size()==0);
    }

    @Test
    void stergeUtilizator_dupa_id() {
        UserDbRepository utilizatori = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","users_test");

        FriendDbRepository prieteni = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","prietenie_test");

        Service srv= new Service(utilizatori,prieteni);

        srv.addUser("nume_1","prenume_1","c.b@gmail.com","pass_2");
        srv.addUser("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.addUser("nume_3","prenume_3","c.b_3@gmail.com","pass_4");

        try{
            srv.deleteUserById(-1);
            assertTrue(false);
        }
        catch(ValidationException e){
            assertTrue(true);
        }

        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        assertTrue(srv.getAllUsers().size()==2);
        assertTrue(srv.getAllUsers().get(0).getEmail().equals("c.b_2@gmail.com"));
        assertTrue(srv.getAllUsers().get(1).getEmail().equals("c.b_3@gmail.com"));

        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        assertTrue(srv.getAllUsers().size()==1);
        assertTrue(srv.getAllUsers().get(0).getEmail().equals("c.b_3@gmail.com"));

        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        assertTrue(srv.getAllUsers().size()==0);
    }

    @Test
    void adaugaPrietenie_dupa_id() {
        UserDbRepository utilizatori = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","users_test");

        FriendDbRepository prieteni = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","prietenie_test");

        Service srv= new Service(utilizatori,prieteni);

        srv.addUser("nume_1","prenume_1","c.b_1@gmail.com","pass_2");
        srv.addUser("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.addUser("nume_3","prenume_3","c.b_3@gmail.com","pass_4");
        srv.addUser("nume_4","prenume_4","c.b_4@gmail.com","pass_5");

        srv.addFriendById(srv.getAllUsers().get(0).get_id(),srv.getAllUsers().get(1).get_id());
        assertTrue(srv.getAllFriends().size()==1);
        assertTrue(srv.getAllFriends().get(0).getPrieten1().equals(srv.getAllUsers().get(0).get_id()));
        assertTrue(srv.getAllFriends().get(0).getPrieten2().equals(srv.getAllUsers().get(1).get_id()));

        try{
            srv.addFriendById(srv.getAllUsers().get(0).get_id(),srv.getAllUsers().get(1).get_id());
            assertTrue(false);
        }catch(ValidationException e){
            assertTrue(true);
        }

        srv.addFriendById(srv.getAllUsers().get(2).get_id(),srv.getAllUsers().get(3).get_id());
        assertTrue(srv.getAllFriends().size()==2);
        assertTrue(srv.getAllFriends().get(1).getPrieten1().equals(srv.getAllUsers().get(2).get_id()));
        assertTrue(srv.getAllFriends().get(1).getPrieten2().equals(srv.getAllUsers().get(3).get_id()));

        srv.addFriendById(srv.getAllUsers().get(1).get_id(),srv.getAllUsers().get(3).get_id());
        assertTrue(srv.getAllFriends().size()==3);
        assertTrue(srv.getAllFriends().get(2).getPrieten1().equals(srv.getAllUsers().get(1).get_id()));
        assertTrue(srv.getAllFriends().get(2).getPrieten2().equals(srv.getAllUsers().get(3).get_id()));

        srv.addFriendById(srv.getAllUsers().get(0).get_id(),srv.getAllUsers().get(3).get_id());
        assertTrue(srv.getAllFriends().size()==4);
        assertTrue(srv.getAllFriends().get(3).getPrieten1().equals(srv.getAllUsers().get(0).get_id()));
        assertTrue(srv.getAllFriends().get(3).getPrieten2().equals(srv.getAllUsers().get(3).get_id()));


        srv.deleteFriendById(srv.getAllUsers().get(0).get_id(),srv.getAllUsers().get(1).get_id());
        srv.deleteFriendById(srv.getAllUsers().get(2).get_id(),srv.getAllUsers().get(3).get_id());
        srv.deleteFriendById(srv.getAllUsers().get(1).get_id(),srv.getAllUsers().get(3).get_id());
        srv.deleteFriendById(srv.getAllUsers().get(0).get_id(),srv.getAllUsers().get(3).get_id());

        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
    }

    @Test
    void adaugaPrietenie() {
        UserDbRepository utilizatori = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","users_test");

        FriendDbRepository prieteni = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","prietenie_test");

        Service srv= new Service(utilizatori,prieteni);

        srv.addUser("nume_1","prenume_1","c.b_1@gmail.com","pass_2");
        srv.addUser("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.addUser("nume_3","prenume_3","c.b_3@gmail.com","pass_4");
        srv.addUser("nume_4","prenume_4","c.b_4@gmail.com","pass_5");

        srv.addFriend(srv.getAllUsers().get(0).getEmail(),srv.getAllUsers().get(1).getEmail());
        assertTrue(srv.getAllFriends().size()==1);
        assertTrue(srv.getAllFriends().get(0).getPrieten1().equals(srv.getAllUsers().get(0).get_id()));
        assertTrue(srv.getAllFriends().get(0).getPrieten2().equals(srv.getAllUsers().get(1).get_id()));

        try{
            srv.addFriend(srv.getAllUsers().get(0).getEmail(),srv.getAllUsers().get(1).getEmail());
            assertTrue(false);
        }catch(ValidationException e){
            assertTrue(true);
        }

        srv.addFriend(srv.getAllUsers().get(2).getEmail(),srv.getAllUsers().get(3).getEmail());
        assertTrue(srv.getAllFriends().size()==2);
        assertTrue(srv.getAllFriends().get(1).getPrieten1().equals(srv.getAllUsers().get(2).get_id()));
        assertTrue(srv.getAllFriends().get(1).getPrieten2().equals(srv.getAllUsers().get(3).get_id()));

        srv.addFriend(srv.getAllUsers().get(1).getEmail(),srv.getAllUsers().get(3).getEmail());
        assertTrue(srv.getAllFriends().size()==3);
        assertTrue(srv.getAllFriends().get(2).getPrieten1().equals(srv.getAllUsers().get(1).get_id()));
        assertTrue(srv.getAllFriends().get(2).getPrieten2().equals(srv.getAllUsers().get(3).get_id()));

        srv.addFriend(srv.getAllUsers().get(0).getEmail(),srv.getAllUsers().get(3).getEmail());
        assertTrue(srv.getAllFriends().size()==4);
        assertTrue(srv.getAllFriends().get(3).getPrieten1().equals(srv.getAllUsers().get(0).get_id()));
        assertTrue(srv.getAllFriends().get(3).getPrieten2().equals(srv.getAllUsers().get(3).get_id()));


        srv.deleteFriendById(srv.getAllUsers().get(0).get_id(),srv.getAllUsers().get(1).get_id());
        srv.deleteFriendById(srv.getAllUsers().get(2).get_id(),srv.getAllUsers().get(3).get_id());
        srv.deleteFriendById(srv.getAllUsers().get(1).get_id(),srv.getAllUsers().get(3).get_id());
        srv.deleteFriendById(srv.getAllUsers().get(0).get_id(),srv.getAllUsers().get(3).get_id());

        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
    }

    @Test
    void stergePrietenie_dupa_id() {
        UserDbRepository utilizatori = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","users_test");

        FriendDbRepository prieteni = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","prietenie_test");

        Service srv= new Service(utilizatori,prieteni);

        srv.addUser("nume_1","prenume_1","c.b_1@gmail.com","pass_2");
        srv.addUser("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.addUser("nume_3","prenume_3","c.b_3@gmail.com","pass_4");
        srv.addUser("nume_4","prenume_4","c.b_4@gmail.com","pass_5");

        srv.addFriend(srv.getAllUsers().get(0).getEmail(),srv.getAllUsers().get(1).getEmail());
        srv.addFriend(srv.getAllUsers().get(2).getEmail(),srv.getAllUsers().get(3).getEmail());
        srv.addFriend(srv.getAllUsers().get(1).getEmail(),srv.getAllUsers().get(3).getEmail());
        srv.addFriend(srv.getAllUsers().get(0).getEmail(),srv.getAllUsers().get(3).getEmail());

        try{
            srv.deleteFriendById(-1,-2);
            assertTrue(false);
        }catch (ValidationException e){
            assertTrue(true);
        }

        srv.deleteFriendById(srv.getAllUsers().get(0).get_id(),srv.getAllUsers().get(1).get_id());
        assertTrue(srv.getAllFriends().size()==3);
        assertTrue(srv.getAllFriends().get(0).getPrieten1().equals(srv.getAllUsers().get(2).get_id()));
        assertTrue(srv.getAllFriends().get(0).getPrieten2().equals(srv.getAllUsers().get(3).get_id()));

        srv.deleteFriendById(srv.getAllUsers().get(2).get_id(),srv.getAllUsers().get(3).get_id());
        assertTrue(srv.getAllFriends().size()==2);
        assertTrue(srv.getAllFriends().get(0).getPrieten1().equals(srv.getAllUsers().get(1).get_id()));
        assertTrue(srv.getAllFriends().get(0).getPrieten2().equals(srv.getAllUsers().get(3).get_id()));

        srv.deleteFriendById(srv.getAllUsers().get(1).get_id(),srv.getAllUsers().get(3).get_id());
        assertTrue(srv.getAllFriends().size()==1);
        assertTrue(srv.getAllFriends().get(0).getPrieten1().equals(srv.getAllUsers().get(0).get_id()));
        assertTrue(srv.getAllFriends().get(0).getPrieten2().equals(srv.getAllUsers().get(3).get_id()));

        srv.deleteFriendById(srv.getAllUsers().get(0).get_id(),srv.getAllUsers().get(3).get_id());
        assertTrue(srv.getAllFriends().size()==0);

        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
    }

    @Test
    void stergePrietenie() {
        UserDbRepository utilizatori = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","users_test");

        FriendDbRepository prieteni = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","prietenie_test");

        Service srv= new Service(utilizatori,prieteni);

        srv.addUser("nume_1","prenume_1","c.b_1@gmail.com","pass_2");
        srv.addUser("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.addUser("nume_3","prenume_3","c.b_3@gmail.com","pass_4");
        srv.addUser("nume_4","prenume_4","c.b_4@gmail.com","pass_5");

        srv.addFriend(srv.getAllUsers().get(0).getEmail(),srv.getAllUsers().get(1).getEmail());
        srv.addFriend(srv.getAllUsers().get(2).getEmail(),srv.getAllUsers().get(3).getEmail());
        srv.addFriend(srv.getAllUsers().get(1).getEmail(),srv.getAllUsers().get(3).getEmail());
        srv.addFriend(srv.getAllUsers().get(0).getEmail(),srv.getAllUsers().get(3).getEmail());

        try{
            srv.deleteFrind("nu_exista_1","nu_exista_2");
            assertTrue(false);
        }catch (ValidationException e){
            assertTrue(true);
        }

        srv.deleteFrind(srv.getAllUsers().get(0).getEmail(),srv.getAllUsers().get(1).getEmail());
        assertTrue(srv.getAllFriends().size()==3);
        assertTrue(srv.getAllFriends().get(0).getPrieten1().equals(srv.getAllUsers().get(2).get_id()));
        assertTrue(srv.getAllFriends().get(0).getPrieten2().equals(srv.getAllUsers().get(3).get_id()));

        srv.deleteFrind(srv.getAllUsers().get(2).getEmail(),srv.getAllUsers().get(3).getEmail());
        assertTrue(srv.getAllFriends().size()==2);
        assertTrue(srv.getAllFriends().get(0).getPrieten1().equals(srv.getAllUsers().get(1).get_id()));
        assertTrue(srv.getAllFriends().get(0).getPrieten2().equals(srv.getAllUsers().get(3).get_id()));

        srv.deleteFrind(srv.getAllUsers().get(1).getEmail(),srv.getAllUsers().get(3).getEmail());
        assertTrue(srv.getAllFriends().size()==1);
        assertTrue(srv.getAllFriends().get(0).getPrieten1().equals(srv.getAllUsers().get(0).get_id()));
        assertTrue(srv.getAllFriends().get(0).getPrieten2().equals(srv.getAllUsers().get(3).get_id()));

        srv.deleteFrind(srv.getAllUsers().get(0).getEmail(),srv.getAllUsers().get(3).getEmail());
        assertTrue(srv.getAllFriends().size()==0);

        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
    }

    @Test
    void get_numar_componente_conexe() {
        UserDbRepository utilizatori = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","users_test");

        FriendDbRepository prieteni = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","prietenie_test");

        Service srv= new Service(utilizatori,prieteni);

        srv.addUser("nume_1","prenume_1","c.b_1@gmail.com","pass_2");
        srv.addUser("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.addUser("nume_3","prenume_3","c.b_3@gmail.com","pass_4");
        srv.addUser("nume_4","prenume_4","c.b_4@gmail.com","pass_5");
        srv.addUser("nume_4","prenume_4","c.b_5@gmail.com","pass_5");
        srv.addUser("nume_4","prenume_4","c.b_6@gmail.com","pass_5");

        srv.addFriend("c.b_1@gmail.com","c.b_2@gmail.com");
        assertTrue(srv.getConexNumber().equals(1));

        srv.addFriend("c.b_3@gmail.com","c.b_4@gmail.com");
        assertTrue(srv.getConexNumber().equals(2));

        srv.addFriend("c.b_1@gmail.com","c.b_4@gmail.com");
        assertTrue(srv.getConexNumber().equals(1));

        srv.addFriend("c.b_5@gmail.com","c.b_6@gmail.com");
        assertTrue(srv.getConexNumber().equals(2));

        srv.deleteFrind("c.b_1@gmail.com","c.b_4@gmail.com");
        assertTrue(srv.getConexNumber().equals(3));

        srv.deleteFriendById(srv.getAllFriends().get(0).getPrieten1(),srv.getAllFriends().get(0).getPrieten2());
        srv.deleteFriendById(srv.getAllFriends().get(0).getPrieten1(),srv.getAllFriends().get(0).getPrieten2());
        srv.deleteFriendById(srv.getAllFriends().get(0).getPrieten1(),srv.getAllFriends().get(0).getPrieten2());

        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
    }

    @Test
    void get_comp_conecta() {
        UserDbRepository utilizatori = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","users_test");

        FriendDbRepository prieteni = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","prietenie_test");

        Service srv= new Service(utilizatori,prieteni);

        srv.addUser("nume_1","prenume_1","c.b_1@gmail.com","pass_2");
        srv.addUser("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.addUser("nume_3","prenume_3","c.b_3@gmail.com","pass_4");
        srv.addUser("nume_4","prenume_4","c.b_4@gmail.com","pass_5");
        srv.addUser("nume_4","prenume_4","c.b_5@gmail.com","pass_5");
        srv.addUser("nume_4","prenume_4","c.b_6@gmail.com","pass_5");

        srv.addFriend("c.b_1@gmail.com","c.b_2@gmail.com");
        assertTrue(srv.getCompConexNumber().size()==2);

        srv.addFriend("c.b_3@gmail.com","c.b_4@gmail.com");
        assertTrue(srv.getCompConexNumber().size()==2);

        srv.addFriend("c.b_1@gmail.com","c.b_4@gmail.com");
        assertTrue(srv.getCompConexNumber().size()==4);

        srv.addFriend("c.b_5@gmail.com","c.b_6@gmail.com");
        assertTrue(srv.getCompConexNumber().size()==4);

        srv.addFriend("c.b_1@gmail.com","c.b_6@gmail.com");
        assertTrue(srv.getCompConexNumber().size()==6);

        srv.deleteFriendById(srv.getAllFriends().get(0).getPrieten1(),srv.getAllFriends().get(0).getPrieten2());
        srv.deleteFriendById(srv.getAllFriends().get(0).getPrieten1(),srv.getAllFriends().get(0).getPrieten2());
        srv.deleteFriendById(srv.getAllFriends().get(0).getPrieten1(),srv.getAllFriends().get(0).getPrieten2());
        srv.deleteFriendById(srv.getAllFriends().get(0).getPrieten1(),srv.getAllFriends().get(0).getPrieten2());
        srv.deleteFriendById(srv.getAllFriends().get(0).getPrieten1(),srv.getAllFriends().get(0).getPrieten2());

        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
    }

    @Test
    void actualizeazaUtilizator() {
        UserDbRepository utilizatori = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","users_test");

        FriendDbRepository prieteni = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","prietenie_test");

        Service srv= new Service(utilizatori,prieteni);

        srv.addUser("nume_1","prenume_1","c.b_1@gmail.com","pass_2");
        srv.updateUser("c.b_1@gmail.com","pass_2","new_f_1","new_l_1","g_1@g.com","newP_1");
        assertTrue(srv.getAllUsers().get(0).getEmail().equals("g_1@g.com"));
        assertTrue(srv.getAllUsers().get(0).getPassword().equals("newP_1"));
        assertTrue(srv.getAllUsers().get(0).getFirstname().equals("new_f_1"));
        assertTrue(srv.getAllUsers().get(0).getLastname().equals("new_l_1"));

        srv.addUser("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.updateUser("c.b_2@gmail.com","pass_3","new_f_2","new_l_2","","newP_2");
        assertTrue(srv.getAllUsers().get(1).getEmail().equals("c.b_2@gmail.com"));
        assertTrue(srv.getAllUsers().get(1).getPassword().equals("newP_2"));
        assertTrue(srv.getAllUsers().get(1).getFirstname().equals("new_f_2"));
        assertTrue(srv.getAllUsers().get(1).getLastname().equals("new_l_2"));

        srv.addUser("nume_3","prenume_3","c.b_3@gmail.com","pass_4");
        srv.updateUser("c.b_3@gmail.com","pass_4","new_f_3","new_l_3","g_3@g.com","newP_3");
        assertTrue(srv.getAllUsers().get(2).getEmail().equals("g_3@g.com"));
        assertTrue(srv.getAllUsers().get(2).getPassword().equals("newP_3"));
        assertTrue(srv.getAllUsers().get(2).getFirstname().equals("new_f_3"));
        assertTrue(srv.getAllUsers().get(2).getLastname().equals("new_l_3"));

        srv.addUser("nume_4","prenume_4","c.b_4@gmail.com","pass_5");
        srv.updateUser("c.b_4@gmail.com","pass_5","new_f_4","new_l_4","g_4@g.com","newP_4");
        assertTrue(srv.getAllUsers().get(3).getEmail().equals("g_4@g.com"));
        assertTrue(srv.getAllUsers().get(3).getPassword().equals("newP_4"));
        assertTrue(srv.getAllUsers().get(3).getFirstname().equals("new_f_4"));
        assertTrue(srv.getAllUsers().get(3).getLastname().equals("new_l_4"));

        srv.addUser("nume_4","prenume_4","c.b_5@gmail.com","pass_5");
        srv.updateUser("c.b_5@gmail.com","pass_5","new_f_5","new_l_5","g_5@g.com","newP_5");
        assertTrue(srv.getAllUsers().get(4).getEmail().equals("g_5@g.com"));
        assertTrue(srv.getAllUsers().get(4).getPassword().equals("newP_5"));
        assertTrue(srv.getAllUsers().get(4).getFirstname().equals("new_f_5"));
        assertTrue(srv.getAllUsers().get(4).getLastname().equals("new_l_5"));

        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
    }

    @Test
    void actualizeazaUtilizator_dupa_id()
    {
        UserDbRepository utilizatori = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","users_test");

        FriendDbRepository prieteni = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","prietenie_test");

        Service srv= new Service(utilizatori,prieteni);

        srv.addUser("nume_1","prenume_1","c.b_1@gmail.com","pass_2");
        srv.updateUserById(srv.getAllUsers().get(0).get_id(),"new_f_1","new_l_1","g_1@g.com","newP_1");

        assertTrue(srv.getAllUsers().get(0).getEmail().equals("g_1@g.com"));
        assertTrue(srv.getAllUsers().get(0).getPassword().equals("newP_1"));
        assertTrue(srv.getAllUsers().get(0).getFirstname().equals("new_f_1"));
        assertTrue(srv.getAllUsers().get(0).getLastname().equals("new_l_1"));

        srv.addUser("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.updateUserById(srv.getAllUsers().get(1).get_id(),"new_f_2","new_l_2","g_2@g.com","newP_2");
        assertTrue(srv.getAllUsers().get(1).getEmail().equals("g_2@g.com"));
        assertTrue(srv.getAllUsers().get(1).getPassword().equals("newP_2"));
        assertTrue(srv.getAllUsers().get(1).getFirstname().equals("new_f_2"));
        assertTrue(srv.getAllUsers().get(1).getLastname().equals("new_l_2"));

        srv.addUser("nume_3","prenume_3","c.b_3@gmail.com","pass_4");
        srv.updateUserById(srv.getAllUsers().get(2).get_id(),"new_f_3","new_l_3","g_3@g.com","newP_3");
        assertTrue(srv.getAllUsers().get(2).getEmail().equals("g_3@g.com"));
        assertTrue(srv.getAllUsers().get(2).getPassword().equals("newP_3"));
        assertTrue(srv.getAllUsers().get(2).getFirstname().equals("new_f_3"));
        assertTrue(srv.getAllUsers().get(2).getLastname().equals("new_l_3"));

        srv.addUser("nume_4","prenume_4","c.b_4@gmail.com","pass_5");
        srv.updateUserById(srv.getAllUsers().get(3).get_id(),"new_f_4","new_l_4","g_4@g.com","newP_4");
        assertTrue(srv.getAllUsers().get(3).getEmail().equals("g_4@g.com"));
        assertTrue(srv.getAllUsers().get(3).getPassword().equals("newP_4"));
        assertTrue(srv.getAllUsers().get(3).getFirstname().equals("new_f_4"));
        assertTrue(srv.getAllUsers().get(3).getLastname().equals("new_l_4"));

        srv.addUser("nume_4","prenume_4","c.b_5@gmail.com","pass_5");
        srv.updateUserById(srv.getAllUsers().get(4).get_id(),"new_f_5","new_l_5","g_5@g.com","newP_5");
        assertTrue(srv.getAllUsers().get(4).getEmail().equals("g_5@g.com"));
        assertTrue(srv.getAllUsers().get(4).getPassword().equals("newP_5"));
        assertTrue(srv.getAllUsers().get(4).getFirstname().equals("new_f_5"));
        assertTrue(srv.getAllUsers().get(4).getLastname().equals("new_l_5"));

        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
    }

    @Test
    void login() {
        UserDbRepository utilizatori = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","users_test");
        FriendDbRepository prieteni = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","prietenie_test");
        Service srv= new Service(utilizatori,prieteni);
        srv.addUser("nume_1","prenume_1","c.b_1@gmail.com","pass_2");
        srv.login("c.b_1@gmail.com","pass_2");
        User u = srv.getLoggedUser();
        assertTrue(u.getEmail().equals("c.b_1@gmail.com"));
        assertTrue(u.getPassword().equals("pass_2"));
        try{
            srv.login("nu_exista","nu_exista");
            assertTrue(false);
        }
        catch (ValidationException e){
            assertTrue(true);
        }

        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
    }

    @Test
    void addFriendToLoginUser() {
        UserDbRepository utilizatori = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","users_test");
        FriendDbRepository prieteni = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","prietenie_test");
        Service srv= new Service(utilizatori,prieteni);
        srv.addUser("nume_1","prenume_1","c.b_1@gmail.com","pass_2");
        srv.addUser("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.login("c.b_1@gmail.com","pass_2");
        User u = srv.getLoggedUser();
        assertTrue(u.getEmail().equals("c.b_1@gmail.com"));
        assertTrue(u.getPassword().equals("pass_2"));
        try{
            srv.login("nu_exista","nu_exista");
            assertTrue(false);
        }
        catch (ValidationException e){
            assertTrue(true);
        }
        srv.addFriendToLoginUser("c.b_2@gmail.com");
        assertTrue(srv.getAllFriends().size()==1);
        assertTrue(srv.getAllFriends().get(0).getPending().equals(true));
        assertTrue(srv.getAllFriends().get(0).getPrieten1().equals(srv.getAllUsers().get(0).get_id()));
        assertTrue(srv.getAllFriends().get(0).getPrieten2().equals(srv.getAllUsers().get(1).get_id()));

        srv.deleteFrind(srv.getAllUsers().get(0).getEmail(),srv.getAllUsers().get(1).getEmail());

        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
    }

    @Test
    void acceptPendingRequest() {
        UserDbRepository utilizatori = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","users_test");
        FriendDbRepository prieteni = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","prietenie_test");
        Service srv= new Service(utilizatori,prieteni);
        srv.addUser("nume_1","prenume_1","c.b_1@gmail.com","pass_2");
        srv.addUser("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.login("c.b_1@gmail.com","pass_2");
        User u = srv.getLoggedUser();
        assertTrue(u.getEmail().equals("c.b_1@gmail.com"));
        assertTrue(u.getPassword().equals("pass_2"));
        try{
            srv.login("nu_exista","nu_exista");
            assertTrue(false);
        }
        catch (ValidationException e){
            assertTrue(true);
        }
        srv.addFriendToLoginUser("c.b_2@gmail.com");
        srv.login("c.b_2@gmail.com","pass_3");
        srv.acceptPendingRequest("c.b_1@gmail.com");

        assertTrue(srv.getAllFriends().size()==1);
        assertTrue(srv.getAllFriends().get(0).getPending().equals(false));
        assertTrue(srv.getAllFriends().get(0).getPrieten1().equals(srv.getAllUsers().get(0).get_id()));
        assertTrue(srv.getAllFriends().get(0).getPrieten2().equals(srv.getAllUsers().get(1).get_id()));

        srv.deleteFrind(srv.getAllUsers().get(0).getEmail(),srv.getAllUsers().get(1).getEmail());

        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
    }

    @Test
    void getAllFriendRequest() {
        UserDbRepository utilizatori = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","users_test");
        FriendDbRepository prieteni = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","prietenie_test");
        Service srv= new Service(utilizatori,prieteni);

        srv.addUser("nume_1","prenume_1","c.b_1@gmail.com","pass_2");
        srv.addUser("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.addUser("nume_3","prenume_3","c.b_3@gmail.com","pass_4");
        srv.login("c.b_1@gmail.com","pass_2");
        srv.addFriendToLoginUser("c.b_3@gmail.com");

        srv.login("c.b_2@gmail.com","pass_3");
        srv.addFriendToLoginUser("c.b_3@gmail.com");

        srv.login("c.b_3@gmail.com","pass_4");

        assertTrue(srv.getAllFriendRequest().size()==2);
        srv.acceptPendingRequest("c.b_2@gmail.com");
        assertTrue(srv.getAllFriendRequest().size()==1);
        srv.acceptPendingRequest("c.b_1@gmail.com");
        assertTrue(srv.getAllFriendRequest().size()==0);

        srv.deleteFrind(srv.getAllUsers().get(0).getEmail(),srv.getAllUsers().get(2).getEmail());
        srv.deleteFrind(srv.getAllUsers().get(1).getEmail(),srv.getAllUsers().get(2).getEmail());

        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
    }


    @Test
    void getAllFriendsOfCurrentUser() {
        UserDbRepository utilizatori = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","users_test");
        FriendDbRepository prieteni = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","prietenie_test");
        Service srv= new Service(utilizatori,prieteni);

        srv.addUser("nume_1","prenume_1","c.b_1@gmail.com","pass_2");
        srv.addUser("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.addUser("nume_3","prenume_3","c.b_3@gmail.com","pass_4");
        srv.login("c.b_1@gmail.com","pass_2");
        srv.addFriendToLoginUser("c.b_3@gmail.com");

        srv.login("c.b_2@gmail.com","pass_3");
        srv.addFriendToLoginUser("c.b_3@gmail.com");

        srv.login("c.b_3@gmail.com","pass_4");

        assertTrue(srv.getAllFriendsOfCurrentUser().size()==0);
        srv.acceptPendingRequest("c.b_2@gmail.com");
        assertTrue(srv.getAllFriendsOfCurrentUser().size()==1);
        srv.acceptPendingRequest("c.b_1@gmail.com");
        assertTrue(srv.getAllFriendsOfCurrentUser().size()==2);

        srv.deleteFrind(srv.getAllUsers().get(0).getEmail(),srv.getAllUsers().get(2).getEmail());
        srv.deleteFrind(srv.getAllUsers().get(1).getEmail(),srv.getAllUsers().get(2).getEmail());

        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
    }

    @Test
    void deleteFriendFromLoggedUser() {
        UserDbRepository utilizatori = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","users_test");
        FriendDbRepository prieteni = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","prietenie_test");
        Service srv= new Service(utilizatori,prieteni);

        srv.addUser("nume_1","prenume_1","c.b_1@gmail.com","pass_2");
        srv.addUser("nume_2","prenume_2","c.b_2@gmail.com","pass_3");
        srv.addUser("nume_3","prenume_3","c.b_3@gmail.com","pass_4");
        srv.login("c.b_1@gmail.com","pass_2");
        srv.addFriendToLoginUser("c.b_3@gmail.com");

        srv.login("c.b_2@gmail.com","pass_3");
        srv.addFriendToLoginUser("c.b_3@gmail.com");

        srv.login("c.b_3@gmail.com","pass_4");

        assertTrue(srv.getAllFriendsOfCurrentUser().size()==0);
        srv.acceptPendingRequest("c.b_2@gmail.com");
        assertTrue(srv.getAllFriendsOfCurrentUser().size()==1);
        srv.acceptPendingRequest("c.b_1@gmail.com");
        assertTrue(srv.getAllFriendsOfCurrentUser().size()==2);
        srv.deleteFriendFromLoggedUser("c.b_1@gmail.com");
        assertTrue(srv.getAllFriendsOfCurrentUser().size()==1);
        try{
            srv.deleteFriendFromLoggedUser("nu_exista");
            assertTrue(false);
        }
        catch(ValidationException v){
            assertTrue(true);
        }
        srv.deleteFriendFromLoggedUser("c.b_2@gmail.com");
        assertTrue(srv.getAllFriendsOfCurrentUser().size()==0);

        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
        srv.deleteUserById(srv.getAllUsers().get(0).get_id());
    }

}
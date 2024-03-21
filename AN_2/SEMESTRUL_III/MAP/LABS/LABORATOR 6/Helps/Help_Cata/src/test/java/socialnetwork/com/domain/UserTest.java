package socialnetwork.com.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    @Test
    void get_firstname() {
        User u=new User(1,"FirstName_1","LastName_2","email_1@gmail.com","passwd_1");
        assertTrue(u.get_id().equals(1));
        assertTrue(u.getFirstname().equals("FirstName_1"));

        User u_2=new User(2,"FirstName_2","LastName_3","email_2@gmail.com","passwd_2");
        assertTrue(u_2.get_id().equals(2));
        assertTrue(u_2.getFirstname().equals("FirstName_2"));

        User u_3=new User(3,"FirstName_3","LastName_4","email_3@gmail.com","passwd_3");
        assertTrue(u_3.get_id().equals(3));
        assertTrue(u_3.getFirstname().equals("FirstName_3"));
    }

    @Test
    void set_firstname() {
        User u=new User(1,"FirstName_1","LastName_2","e_1@g.com","pass_1");
        u.set_id(11);
        u.setFirstname("new_firstname_1");
        assertTrue(u.getFirstname().equals("new_firstname_1"));

        User u_2=new User(1,"FirstName_1","LastName_2","e_1@g.com","pass_1");
        u_2.set_id(22);
        u_2.setFirstname("new_firstname_2");
        assertTrue(u_2.get_id().equals(22));
        assertTrue(u_2.getFirstname().equals("new_firstname_2"));

        User u_3=new User(2,"FirstName_2","LastName_2","e_1@g.com","pass_1");
        u_3.set_id(33);
        u_3.setFirstname("new_firstname_3");
        assertTrue(u_3.get_id().equals(33));
        assertTrue(u_3.getFirstname().equals("new_firstname_3"));
    }

    @Test
    void get_lastname() {
        User u=new User(1,"FirstName_1","LastName_2","email_1@gmail.com","passwd_1");
        assertTrue(u.get_id().equals(1));
        assertTrue(u.getLastname().equals("LastName_2"));

        User u_2=new User(2,"FirstName_2","LastName_3","email_2@gmail.com","passwd_2");
        assertTrue(u_2.get_id().equals(2));
        assertTrue(u_2.getLastname().equals("LastName_3"));

        User u_3=new User(3,"FirstName_3","LastName_4","email_3@gmail.com","passwd_3");
        assertTrue(u_3.get_id().equals(3));
        assertTrue(u_3.getLastname().equals("LastName_4"));
    }

    @Test
    void set_lastname() {
        User u=new User(1,"FirstName_1","LastName_2","e_1@g.com","pass_1");
        u.set_id(11);
        u.setLastname("new_lastname_1");
        assertTrue(u.get_id().equals(11));
        assertTrue(u.getLastname().equals("new_lastname_1"));

        User u_2=new User(1,"FirstName_1","LastName_2","e_1@g.com","pass_1");
        u_2.set_id(22);
        u_2.setLastname("new_lastname_2");
        assertTrue(u_2.get_id().equals(22));
        assertTrue(u_2.getLastname().equals("new_lastname_2"));

        User u_3=new User(2,"FirstName_2","LastName_2","e_1@g.com","pass_1");
        u_3.set_id(33);
        u_3.setLastname("new_lastname_3");
        assertTrue(u_3.get_id().equals(33));
        assertTrue(u_3.getLastname().equals("new_lastname_3"));
    }

    @Test
    void get_email() {
        User u=new User(1,"FirstName_1","LastName_2","email_1@gmail.com","passwd_1");
        assertTrue(u.get_id().equals(1));
        assertTrue(u.getEmail().equals("email_1@gmail.com"));

        User u_2=new User(2,"FirstName_2","LastName_3","email_2@gmail.com","passwd_2");
        assertTrue(u_2.get_id().equals(2));
        assertTrue(u_2.getEmail().equals("email_2@gmail.com"));

        User u_3=new User(3,"FirstName_3","LastName_4","email_3@gmail.com","passwd_3");
        assertTrue(u_3.get_id().equals(3));
        assertTrue(u_3.getEmail().equals("email_3@gmail.com"));
    }

    @Test
    void set_email() {
        User u=new User(1,"FirstName_1","LastName_2","e_1@g.com","pass_1");
        u.set_id(11);
        u.setEmail("new_e_1@g.com");
        assertTrue(u.get_id().equals(11));
        assertTrue(u.getEmail().equals("new_e_1@g.com"));

        User u_2=new User(1,"FirstName_1","LastName_2","e_1@g.com","pass_1");
        u_2.set_id(22);
        u_2.setEmail("new_e_2@g.com");
        assertTrue(u_2.get_id().equals(22));
        assertTrue(u_2.getEmail().equals("new_e_2@g.com"));

        User u_3=new User(2,"FirstName_2","LastName_2","e_1@g.com","pass_1");
        u_3.set_id(33);
        u_3.setEmail("new_e_3@g.com");
        assertTrue(u_3.get_id().equals(33));
        assertTrue(u_3.getEmail().equals("new_e_3@g.com"));
    }

    @Test
    void get_parola() {
        User u=new User(1,"FirstName_1","LastName_2","email_1@gmail.com","passwd_1");
        assertTrue(u.get_id().equals(1));
        assertTrue(u.getPassword().equals("passwd_1"));

        User u_2=new User(2,"FirstName_2","LastName_3","email_2@gmail.com","passwd_2");
        assertTrue(u_2.get_id().equals(2));
        assertTrue(u_2.getPassword().equals("passwd_2"));

        User u_3=new User(3,"FirstName_3","LastName_4","email_3@gmail.com","passwd_3");
        assertTrue(u_3.get_id().equals(3));
        assertTrue(u_3.getPassword().equals("passwd_3"));
    }

    @Test
    void set_parola() {
        User u=new User(1,"FirstName_1","LastName_2","e_1@g.com","pass_1");
        u.set_id(11);
        u.setPassword("new_pass_1");
        assertTrue(u.get_id().equals(11));
        assertTrue(u.getPassword().equals("new_pass_1"));

        User u_2=new User(1,"FirstName_1","LastName_2","e_1@g.com","pass_1");
        u_2.set_id(22);
        u_2.setPassword("new_pass_2");
        assertTrue(u_2.get_id().equals(22));
        assertTrue(u_2.getPassword().equals("new_pass_2"));

        User u_3=new User(2,"FirstName_2","LastName_2","e_1@g.com","pass_1");
        u_3.set_id(33);
        u_3.setPassword("new_pass_3");
        assertTrue(u_3.get_id().equals(33));
        assertTrue(u_3.getPassword().equals("new_pass_3"));
    }

    @Test
    void testEquals() {
        Friend p_1=new Friend(1,2);
        Friend p_2=new Friend(1,2);
        Friend p_3=new Friend(5,6);
        assertTrue(p_1.equals(p_2));
        assertTrue(!p_2.equals(p_3));
        assertTrue(!p_1.equals(p_3));
    }
}
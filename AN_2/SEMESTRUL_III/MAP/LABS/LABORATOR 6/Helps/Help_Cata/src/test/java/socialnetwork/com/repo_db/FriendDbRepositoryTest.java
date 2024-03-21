package socialnetwork.com.repo_db;

import org.junit.jupiter.api.Test;
import socialnetwork.com.domain.Friend;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FriendDbRepositoryTest {

    @Test
    void adauga() {
        FriendDbRepository r = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","prietenie_test");

        Friend p_1=new Friend(1,2);
        r.add(p_1);
        assertTrue(r.getAll().size()==1);
        Friend p = r.getAll().get(0);
        assertTrue(r.getAll().get(0).equals(p_1));

        Friend p_2=new Friend(3,4);
        r.add(p_2);
        assertTrue(r.getAll().size()==2);
        assertTrue(r.getAll().get(1).equals(p_2));

        Friend p_3=new Friend(5,6);
        r.add(p_3);
        assertTrue(r.getAll().size()==3);
        assertTrue(r.getAll().get(2).equals(p_3));

        Friend p_4 = new Friend(5,8,false);
        r.add(p_4);
        assertTrue(r.getAll().size()==4);
        assertTrue(r.getAll().get(3).equals(p_4));

        r.delete(p_1);
        r.delete(p_2);
        r.delete(p_3);
        r.delete(p_4);
    }

    @Test
    void sterge() {
        FriendDbRepository r = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","prietenie_test");

        Friend p_1=new Friend(1,2);
        r.add(p_1);
        assertTrue(r.delete(p_1).equals(p_1));
        assertTrue(r.getAll().size()==0);

        Friend p_2=new Friend(2,3);
        Friend p_3=new Friend(1,3);
        r.add(p_1);
        r.add(p_2);
        r.add(p_3);

        assertTrue(r.delete(p_2).equals(p_2));
        assertTrue(r.getAll().size()==2);
        assertTrue(r.delete(p_1).equals(p_1));
        assertTrue(r.getAll().size()==1);
        assertTrue(r.delete(p_3).equals(p_3));
        assertTrue(r.getAll().size()==0);
    }


    @Test
    void update() {
        FriendDbRepository r = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","prietenie_test");

        Friend p_1=new Friend(1,2);
        r.add(p_1);
        assertTrue(r.delete(p_1).equals(p_1));
        assertTrue(r.getAll().size()==0);

        Friend p_2=new Friend(2,3);
        Friend p_3=new Friend(1,3);
        r.add(p_1);
        r.add(p_2);
        r.add(p_3);
        Friend n_1 = new Friend(1,5);
        Friend n_2 = new Friend(2,7);
        Friend n_3 = new Friend(3,6);
        r.update(p_1,n_1);
        r.update(p_2,n_2);
        r.update(p_3,n_3);

        assertTrue(r.delete(n_2).equals(n_2));
        assertTrue(r.getAll().size()==2);
        assertTrue(r.delete(n_1).equals(n_1));
        assertTrue(r.getAll().size()==1);
        assertTrue(r.delete(n_3).equals(n_3));
        assertTrue(r.getAll().size()==0);
    }

    @Test
    void findById() {
        FriendDbRepository r = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres","prietenie_test");

        Friend p_1=new Friend(1,2);
        r.add(p_1);
        assertTrue(r.findById(1,2).equals(p_1));
        assertTrue(r.getAll().size()==1);

        Friend p_2=new Friend(3,4);
        r.add(p_2);
        assertTrue(r.findById(3,4).equals(p_2));
        assertTrue(r.getAll().size()==2);

        Friend p_3=new Friend(5,6,false);
        r.add(p_3);
        assertTrue(r.findById(5,6).equals(p_3));
        assertTrue(r.getAll().size()==3);


        r.delete(p_1);
        r.delete(p_2);
        r.delete(p_3);
    }
}
package socialnetwork.com.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FriendTest {

    @Test
    void get_prieten_1() {
        Friend p_1=new Friend(1,2);
        assertTrue(p_1.getPrieten1().equals(1));

        Friend p_2=new Friend(3,4);
        assertTrue(p_2.getPrieten1().equals(3));

        Friend p_3=new Friend(5,6);
        assertTrue(p_3.getPrieten1().equals(5));
    }

    @Test
    void get_prieten_2() {
        Friend p_1=new Friend(1,2);
        assertTrue(p_1.getPrieten2().equals(2));

        Friend p_2=new Friend(3,4);
        assertTrue(p_2.getPrieten2().equals(4));

        Friend p_3=new Friend(5,6);
        assertTrue(p_3.getPrieten2().equals(6));
    }

    @Test
    void get_data() {
        LocalDateTime d = LocalDateTime.now();
        Friend p_1=new Friend(1,2,d);
        assertTrue(p_1.getPrieten1().equals(1));
        assertTrue(p_1.getPrieten2().equals(2));
        assertTrue(p_1.getData().equals(d));

        d=LocalDateTime.now();
        Friend p_2=new Friend(3,4,d);
        assertTrue(p_2.getPrieten1().equals(3));
        assertTrue(p_2.getPrieten2().equals(4));
        assertTrue(p_2.getData().equals(d));

        d=LocalDateTime.now();
        Friend p_3=new Friend(5,6,d);
        assertTrue(p_3.getPrieten1().equals(5));
        assertTrue(p_3.getData().equals(d));
        assertTrue(p_3.getPrieten2().equals(6));
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

    @Test
    void getPending() {
        Friend p_1 = new Friend(1,2,true);
        Friend p_2 = new Friend(1,2,false);
        assertTrue(p_1.getPending().equals(true));
        assertTrue(p_2.getPending().equals(false));
    }

    @Test
    void setPending() {
        Friend p_1 = new Friend(1,2,true);
        Friend p_2 = new Friend(1,2,false);
        p_1.setPending(false);
        p_2.setPending(true);
        assertTrue(p_2.getPending().equals(true));
        assertTrue(p_1.getPending().equals(false));
    }
}
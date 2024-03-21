package teste;
import static org.junit.Assert.assertEquals;

import domain.Prietenie;
import domain.Utilizator;
import org.junit.Test;

import java.time.LocalDateTime;

public class TesteDomain {

    @Test
    public void testUtilizator(){
        Utilizator u=new Utilizator("1","a@yahoo.com","1","a","a");
        assertEquals(u.getId(),"1");
        assertEquals(u.getEmail(),"a@yahoo.com");
        assertEquals(u.getParola(),"1");
        assertEquals(u.getNume(),"a");
        assertEquals(u.getPrenume(),"a");

        //u.setId("2");
        u.setEmail("b@yahoo.com");
        u.setParola("2");
        u.setNume("b");
        u.setPrenume("b");

        assertEquals(u.getId(),"1");
        assertEquals(u.getEmail(),"b@yahoo.com");
        assertEquals(u.getParola(),"2");
        assertEquals(u.getNume(),"b");
        assertEquals(u.getPrenume(),"b");
    }

    @Test
    public void testPrietenie() {
        Prietenie p = new Prietenie("isudecbwk", "ebcqui", "wewedwed", LocalDateTime.now());
        assertEquals(p.getId(),"isudecbwk");
        assertEquals(p.getIdPrieten1(),"ebcqui");
        assertEquals(p.getIdPrieten2(),"wewedwed");

        //nu am settere, id-urile sunt finale
    }
}

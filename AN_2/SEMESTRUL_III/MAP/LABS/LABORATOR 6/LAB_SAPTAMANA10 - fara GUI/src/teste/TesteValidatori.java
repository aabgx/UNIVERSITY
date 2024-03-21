package teste;

import domain.Prietenie;
import domain.Utilizator;
import domain.validators.ValidationException;
import domain.validators.ValidatorPrietenie;
import domain.validators.ValidatorUtilizator;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

public class TesteValidatori {

    @Test
    public void testValidatorUtilizator() {
        ValidatorUtilizator v = new ValidatorUtilizator();
        Utilizator u1 = new Utilizator("", "", "", "", "");
        try {
            v.validate(u1);
            assertTrue(false);
        } catch (ValidationException e) {
            assertTrue(true);
        }

        Utilizator u2 = new Utilizator("sdcc", "fgbsx", "erfc", "", "Maria");
        try {
            v.validate(u2);
            assertTrue(false);
        } catch (
                ValidationException e) {
            assertTrue(true);
        }

        Utilizator u3 = new Utilizator("AAA", "DDD", "CCCC", "Oarga", "aa");
        try {
            v.validate(u3);
            assertTrue(false);
        } catch (ValidationException e) {
            assertTrue(true);
        }

        Utilizator u4 = new Utilizator("sdasd12312", "dha@yahoo.com", "adsadsas", "Bugnar", "Andreea");
        try {
            v.validate(u4);
            assertTrue(true);
        } catch (ValidationException e) {
            assertTrue(false);
        }
    }

    public void testValidatorPrietenie(){
        ValidatorPrietenie v= new ValidatorPrietenie();

        Prietenie p1=new Prietenie("","","", LocalDateTime.now());
        try{
            v.validate(p1);
            assertTrue(false);
        }catch(ValidationException e){
            assertTrue(true);
        }

        Prietenie p2=new Prietenie("srfws","aefdqaw","",LocalDateTime.now());
        try{
            v.validate(p2);
            assertTrue(false);
        }catch(ValidationException e){
            assertTrue(true);
        }

        Prietenie p3=new Prietenie("","erfvgew","erfwef",LocalDateTime.now());
        try{
            v.validate(p3);
            assertTrue(false);
        }catch(ValidationException e){
            assertTrue(true);
        }

        Prietenie p4=new Prietenie("wfaewe","wevqwc","wfcwde",LocalDateTime.now());
        try{
            v.validate(p4);
            assertTrue(false);
        }catch(ValidationException e){
            assertTrue(true);
        }

    }
}

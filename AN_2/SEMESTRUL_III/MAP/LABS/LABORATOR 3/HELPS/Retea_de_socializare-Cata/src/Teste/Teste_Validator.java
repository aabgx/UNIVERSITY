package Teste;

import domain.Prietenie;
import domain.Utilizator;
import validators.ValidationException;
import validators.ValidatorPrietenie;
import validators.ValidatorUtilizator;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class Teste_Validator {
    @Test
    public void test_validator_Utilizator(){
        ValidatorUtilizator v=new ValidatorUtilizator();
        Utilizator u_1=new Utilizator("","","","","");
        try{
            v.validate(u_1);
            assertTrue(false);
        }
        catch(ValidationException e){
            assertTrue(true);
        }

        Utilizator u_2=new Utilizator("AS","ASDAS","ASDDA","","DSADSA");
        try{
            v.validate(u_2);
            assertTrue(false);
        }
        catch(ValidationException e){
            assertTrue(true);
        }

        Utilizator u_3=new Utilizator("AAA","DDD","CCCC","c.b@gmail.com","");
        try{
            v.validate(u_3);
            assertTrue(false);
        }
        catch(ValidationException e){
            assertTrue(true);
        }

        Utilizator u_4=new Utilizator("sdasd12312","asdasd","adsadsas","cata.bugn@gmail.com","passwd_2");
        try{
            v.validate(u_4);
            assertTrue(true);
        }
        catch(ValidationException e){
            assertTrue(false);
        }
    }

    @Test
    public void test_validator_Prietenie(){
        ValidatorPrietenie v= new ValidatorPrietenie();
        Prietenie p_1 = new Prietenie("id_1","prieten_1","");
        try{
            v.validate(p_1);
            assertTrue(false);
        }
        catch(ValidationException e){
            assertTrue(true);
        }
        Prietenie p_2 = new Prietenie("id_2","","");
        try{
            v.validate(p_2);
            assertTrue(false);
        }
        catch(ValidationException e){
            assertTrue(true);
        }
        Prietenie p_3 = new Prietenie("id_1","prieten_1","prieten_5");
        try{
            v.validate(p_3);
            assertTrue(true);
        }
        catch(ValidationException e){
            assertTrue(false);
        }
    }
}

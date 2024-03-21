package socialnetwork.com.validators;

import org.junit.jupiter.api.Test;
import socialnetwork.com.domain.User;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorUserTest {

    @Test
    void validate() {
        ValidatorUtilizator v=new ValidatorUtilizator();
        User u_1=new User(0,"","","","");
        try{
            v.validate(u_1);
            assertTrue(false);
        }
        catch(ValidationException e){
            assertTrue(true);
        }

        User u_2=new User(1,"ASDAS","ASDDA","","DSADSA");
        try{
            v.validate(u_2);
            assertTrue(false);
        }
        catch(ValidationException e){
            assertTrue(true);
        }

        User u_3=new User(2,"DDD","CCCC","c.b@gmail.com","");
        try{
            v.validate(u_3);
            assertTrue(false);
        }
        catch(ValidationException e){
            assertTrue(true);
        }

        User u_4=new User(3,"asdasd","adsadsas","cata.bugn@gmail.com","passwd_2");
        try{
            v.validate(u_4);
            assertTrue(true);
        }
        catch(ValidationException e){
            assertTrue(false);
        }
    }
}
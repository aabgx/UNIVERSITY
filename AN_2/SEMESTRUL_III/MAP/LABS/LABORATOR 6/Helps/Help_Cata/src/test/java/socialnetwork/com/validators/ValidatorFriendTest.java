package socialnetwork.com.validators;

import org.junit.jupiter.api.Test;
import socialnetwork.com.domain.Friend;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorFriendTest {

    @Test
    void validate() {
        ValidatorPrietenie v= new ValidatorPrietenie();
        Friend p_1 = new Friend(0,1);
        try{
            v.validate(p_1);
            assertTrue(false);
        }
        catch(ValidationException e){
            assertTrue(true);
        }
    }
}
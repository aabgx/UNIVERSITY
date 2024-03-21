package domain;

import java.util.Random;

public class Entity<ID> {
    private ID id;

//    public String generateID(){
//        int leftLimit= 48;  //'0'
//        int rightLimit= 122;  //'z'
//        int targetStringLength= 10;
//        Random random= new Random();
//
//        String generatedID= random.ints(leftLimit, rightLimit+1)
//                .filter(i->(i<=57||i>=65)&&(i<=90||i>=97))
//                .limit(targetStringLength)
//                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
//                .toString();
//        return generatedID;
//    }

//    public Entity(ID id) {
//        this.id= id;
//   }

    /**
     * Returns id of current entity
     * @return ID, id of current entity
     */
    public ID getId() {
        return id;
    }

    /**
     * Sets id of current entity
     * @param id, ID, new id
     */
    public void setId(ID id) {
        this.id = id;
    }
}

package domain;

import domain.exceptions.EntityAlreadyFound;
import domain.exceptions.EntityNotFound;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User extends Entity<Long>{
    private String firstName;
    private String lastname;
    private String email;
    private List<User> friends;

    /**
     * Variabila statica prin care generam id-uri diferite
     */
    private static Long prevId=0L;

//    public String generateID(){
//        int leftLimit= 48;  //'0'
//        int rightLimit= 122;  //'z'
//        int targetStringLength= 10;
//        Random random= new Random();
//        String generatedID= random.ints(leftLimit, rightLimit+1)
//                .filter(i->(i<=57||i>=65)&&(i<=90||i>=97))
//                .limit(targetStringLength)
//                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
//                .toString();
//       return generatedID;
//   }

    /**
     * Generates a new id
     * @return generatedID, Long, newly generated id
     */
   public static Long generateID(){
        Long generatedId= prevId+1;
        prevId++;
        return generatedId;
   }

    public User(String firstName, String lastname, String email) {
        //if(getId()==null) super.setId(this.generateID());
        //super.setId(this.generateID());
        this.firstName = firstName;
        this.lastname = lastname;
        this.email=email;
        this.friends= new ArrayList<User>();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastname;
    }

    public String getEmail(){return email; };

    public List<User> getFriends() {
        return friends;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    /**
     * Adds friend in friend list of user
     * @param friend: User, friend to be added in friend list
     * @throws EntityAlreadyFound if user already exists in friend list
     */
    public void addFriend(User friend) throws EntityAlreadyFound {
       if(!friends.contains(friend)){
           this.friends.add(friend);
       }
       else throw new EntityAlreadyFound("Utilizatorul exista deja in lista de prieteni!");
    }

    /**
     * Removes friend from friend list
     * @param friend: User, the friend to be removed from friend list
     * @throws EntityNotFound if given User does not exist in friend list
     */
    public void removeFriend(User friend) throws EntityNotFound {
       if(friends.contains(friend)){
           this.friends.remove(friend);
       }
       else throw new EntityNotFound("Utilizatorul nu exista in lista de prieteni!");
    }

    public void removeAllFriends(){
       this.friends.clear();
    }

    public String getFriendsString(){
        String s = new String();
        for(User u : this.friends){
            s+= "["+u.getFirstName() +" " + u.getLastName() + "]";
        }
        return s;
    }

    @Override
    public String toString() {
        return "User{ " +
                "id='"+ Long.toString(super.getId()) +'\''+
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastname + '\'' +
                ", email='"+email+'\''+
                ", friends='"+ this.getFriendsString()+'\''+
                '}';
    }

    /**
     * Defines equality between 2 users
     * User1 is equal to User2 if they have equal ids, firstNames and lastNames
     * @param o: Object, entity to which we test equality
     * @return bool, True if the 2 user satisfy equality conditions,
     * false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(super.getId(), user.getId())&&Objects.equals(firstName, user.firstName) && Objects.equals(lastname, user.lastname);
    }

    /**
     * Overrides hashCode method such that User receives hashCode based on
     * id, firstName and lastName
     * @return int, the generated hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), firstName, lastname);
    }
}

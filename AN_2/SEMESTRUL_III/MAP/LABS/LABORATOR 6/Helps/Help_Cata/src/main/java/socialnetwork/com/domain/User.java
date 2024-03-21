package socialnetwork.com.domain;


import java.util.Objects;

public class User extends Entity<Integer>{
    //private String id;
    private String firstname;
    private String lastname;
    private String email;

    private String password;

    /***
     * Constructorul implicit
     * @param id - id-ul utilizatorului
     * @param firstname - prenumele utilizatorului
     * @param lastname - numele utilizatorului
     * @param email - email-ul utilizatorului
     * @param password - parola utilizatorului
     */
    public User(Integer id, String firstname, String lastname, String email, String password){
        super(id);
        this.firstname=firstname;
        this.lastname=lastname;
        this.email=email;
        this.password = password;
    }

    /***
     * Returneaza prenumele utilizatorului
     * @return Prenumele utilizatorului
     */
    public String getFirstname(){
        return firstname;
    }

    /***
     * Seteaza prenumele utilizatorului
     * @param firstname prenumele utilizatorului
     */
    public void setFirstname(String firstname){
        this.firstname=firstname;
    }

    /***
     * Returneaza numele utilizatorului
     * @return numele utilizatorului
     */
    public String getLastname(){
        return lastname;
    }

    /***
     * Seteaza numele utilizatorului
     * @param lastname numele utilizatorului
     */
    public void setLastname(String lastname){
        this.lastname=lastname;
    }

    /***
     * Determina email-ul utilizatorului
     * @return email-ul utilizatorului
     */
    public String getEmail(){
        return email;
    }

    /***
     * Seteaza emailul utilizatoruliu
     * @param email emailul utilizatoruluis
     */
    public void setEmail(String email){
        this.email=email;
    }

    /***
     * Determina parola utilizatorului
     * @return parola utilizatorului
     */
    public String getPassword(){
        return password;
    }

    /***
     * Seteaza parola utilizatorului
     * @param parola parola utilizatorului
     */
    public void setPassword(String password){
        this.password =password;
    }

    @Override
    public String toString(){
        return "ID: " + get_id()+ " | FirstName: " + firstname +" | LastName: "+lastname+" | Email: " + email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User that = (User) o;
        return get_id().equals(that.get_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(get_id());
    }

}

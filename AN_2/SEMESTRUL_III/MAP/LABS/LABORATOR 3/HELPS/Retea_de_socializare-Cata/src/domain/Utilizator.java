package domain;


import java.util.List;
import java.util.Objects;

public class Utilizator extends Entity<String>{
    //private String id;
    private String firstname;
    private String lastname;

    private String email;

    private String parola;

    public Utilizator(String id,String firstname,String lastname,String email,String parola){
        super(id);
        this.firstname=firstname;
        this.lastname=lastname;
        this.email=email;
        this.parola=parola;
    }

    public String get_firstname(){
        return firstname;
    }

    public void set_firstname(String firstname){
        this.firstname=firstname;
    }

    public String get_lastname(){
        return lastname;
    }

    public void set_lastname(String lastname){
        this.lastname=lastname;
    }

    public String get_email(){
        return email;
    }

    public void set_email(String email){
        this.email=email;
    }

    public String get_parola(){
        return parola;
    }

    public void set_parola(String parola){
        this.parola=parola;
    }

    @Override
    public String toString(){
        return "ID: " + get_id()+ " | FirstName: " + firstname +" | LastName: "+lastname+" | Email: " + email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator)) return false;
        Utilizator that = (Utilizator) o;
        return get_id().equals(that.get_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(get_id());
    }
}

package domain;

import java.time.LocalDate;


public class User extends Entity {

    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private LocalDate birthday;

    /**
     * User constructor
     * @param id String
     * @param firstName String
     * @param lastName String
     * @param email String
     * @param password String
     * @param birthday LocalDate
     */
    public User(String id, String firstName, String lastName, String email, String password, LocalDate birthday) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = password;
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.passwordHash = password;
    }

    public String getPassword() {
        return passwordHash;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}

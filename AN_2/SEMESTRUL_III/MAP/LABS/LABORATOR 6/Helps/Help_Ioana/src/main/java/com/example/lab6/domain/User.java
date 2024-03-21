package com.example.lab6.domain;
import java.util.Objects;

public class User extends Entity<Integer>{
    private String numeUser;
    private String parola;
    public User(String numeUser,String parola)
    {
        this.numeUser = numeUser;
        this.parola = parola;
    }
    public User(){}
    @Override
    public String toString()
    {
        return "  " + this.getId() + ", " + this.getNume() + ", " + this.getParola() + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(numeUser, user.numeUser) && Objects.equals(parola, user.parola);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeUser, parola);
    }

    public String getNume() {
        return numeUser;
    }
    public String getParola() {
        return parola;
    }
}

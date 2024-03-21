package com.domain;

import java.util.Objects;

public class Utilizator extends Entitate<String>{
    private String email;
    private String parola;
    private String nume;
    private String prenume;

    //constructor
    public Utilizator(String id, String email,String parola, String nume, String prenume){
        super(id);
        this.email=email;
        this.parola=parola;
        this.nume=nume;
        this.prenume=prenume;
    }

    //metode suprascrise
    @Override
    public String toString() {return id+" "+email+" "+parola+" "+nume+" "+prenume;}
    @Override
    public int hashCode(){return Objects.hash(email,parola,nume,prenume);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Utilizator that = (Utilizator) o;
        return Objects.equals(email, that.email) && Objects.equals(parola, that.parola) && Objects.equals(nume, that.nume) && Objects.equals(prenume, that.prenume);
    }

    //gettere si settere
    public void setEmail(String id) {
        this.email = id;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }
    public void setPrenume(String prenume){
        this.prenume=prenume;
    }
    public String getEmail() {
        return email;
    }
    public String getNume() {
        return nume;
    }
    public String getPrenume(){
        return prenume;
    }
    public String getParola() {
        return parola;
    }
    public void setParola(String parola) {
        this.parola = parola;
    }
}

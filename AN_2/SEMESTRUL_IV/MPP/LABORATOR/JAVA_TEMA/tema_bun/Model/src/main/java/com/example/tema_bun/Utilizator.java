package com.example.tema_bun;

public class Utilizator extends Entitate<Integer> {
    private String username;
    private String parola;
    private Integer id;

    public Utilizator(String username,String parola){
        this.username=username;
        this.parola=parola;
    }

    public Utilizator(Integer id,String username,String parola){
        this.id=id;
        this.username=username;
        this.parola=parola;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }

    @Override
    public Integer getId() {
        return id;
    }
}

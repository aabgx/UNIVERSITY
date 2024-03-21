package com.example.tema_bun;

public class Copil extends Entitate<Integer> {
    private String nume;
    private Integer varsta;

    private Integer id;

    public Copil(String nume, Integer varsta) {
        this.nume = nume;
        this.varsta = varsta;
    }
    public Copil(Integer id, String nume, Integer varsta){
        this.id=id;
        this.nume = nume;
        this.varsta = varsta;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getVarsta() {
        return varsta;
    }

    public void setVarsta(Integer varsta) {
        this.varsta = varsta;
    }

//    @Override
//    public void setId(Integer id) {
//        this.id=id;
//    }
//
//    @Override
//    public Integer getId() {
//        return id;
//    }
}

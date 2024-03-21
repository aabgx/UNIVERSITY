package com.example.tema_bun.domain;

public class Inscriere implements Entitate<Integer>{
    private Integer id;
    private Integer id_copil;
    private String id_proba;

    public Inscriere(Integer id_copil, String id_proba){
        this.id_copil=id_copil;
        this.id_proba=id_proba;
    }

    public Inscriere(Integer id,Integer id_copil, String id_proba){
        this.id=id;
        this.id_copil=id_copil;
        this.id_proba=id_proba;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public Integer getId_copil() {
        return id_copil;
    }

    public void setId_copil(Integer id_copil) {
        this.id_copil = id_copil;
    }

    public String getId_proba() {
        return id_proba;
    }

    public void setId_proba(String id_proba) {
        this.id_proba = id_proba;
    }
}

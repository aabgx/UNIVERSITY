package com.example.tema_bun.domain;

import java.util.Objects;

public class Proba implements Entitate<String>{
    private String id;

    public Proba(){
    }
    public Proba(String id){
        this.id=id;
    }

    @Override
    public void setId(String id) {
        this.id=id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Proba{" +
                "id=" + id +
                '}';
    }
}

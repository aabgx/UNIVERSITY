package com.example.lab6.domain;

public class Nod
{
    private String colour;
    private int distanta;
    private User valoare;
    private int id;
    private Nod parinte;
    public String getColour() {
        return colour;
    }
    public int getDistanta() {
        return distanta;
    }

    public Nod getParinte() {
        return parinte;
    }

    @Override
    public String toString() {
        return this.getValoare().toString();
    }

    public void setParinte(Nod parinte) {
        this.parinte = parinte;
    }

    public User getValoare()
    {
        return valoare;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setColour(String colour) {
        this.colour = colour;
    }
    public void setDistanta(int distanta) {
        this.distanta = distanta;
    }
    public void setValoare(User valoare) {
        this.valoare = valoare;
    }
}

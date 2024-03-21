package com.example.lab6.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Network {
    protected List<Friendship> retea;
    protected List<Community> componenteConexe = new ArrayList<>();
    protected int number;
    protected List<Integer> drumMaxim = new ArrayList<>();
    protected int maxim;
    protected List<Nod> secventaMax = new ArrayList<>();
    protected Community sociabila;
    public Network()
    {
        this.retea = new ArrayList<>();
        number=0;
    }
    public abstract void componenteConexe();
    public List<Community> getComponenteConexe() {
        return componenteConexe;
    }
    public int getNumber() {
        return number;
    }
    public Community getDrumLung()
    {
        return sociabila;
    }
    public List<Nod> secventaDrumLung(){return secventaMax;}

    public void setSecventaMax(List<Nod> secventaMax) {
        this.secventaMax = secventaMax;
    }

    public void setSociabila(Community sociabila) {
        this.sociabila = sociabila;
    }

}

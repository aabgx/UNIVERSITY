package com.domain;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class Entitate<ID> {
    //final sa nu poata fi modificat in clasele derivate
    protected final ID id;

    /***
     * constructor
     */
    public Entitate(ID id){
        if(id==null){
            this.id =  (ID)UUID.randomUUID().toString();
        }
        else{
            this.id=id;
        }
    }

    //getter id
    public ID getId() {
        return id;
    }
    //public void setId(ID id) {this.id=id;}

    /***
     * metoda suprascrisa equals
     * @param o - cel cu care comapram
     * @return true daca sunt egale, false altfe;
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entitate entitate = (Entitate) o;
        return Objects.equals(id, entitate.id);
    }

    /***
     * Metoda suprascrisa hashCode
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /***
     * Metoda suprascrisa toString
     * @return sirul corespunzator entitatii
     */
    @Override
    public String toString() {
        return "Entitate{" + "id='" + id + '\'' + '}';
    }
}

package domain;

import java.util.Objects;

public class Prietenie extends Entity<String>{

    private String id_prieten_1,id_prieten_2;

    public Prietenie(String id,String id_prieten_1,String id_prieten_2) {
        super(id);
        this.id_prieten_1=id_prieten_1;
        this.id_prieten_2=id_prieten_2;
    }

    public String get_prieten_1(){
        return id_prieten_1;
    }

    public String get_prieten_2(){
        return id_prieten_2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prietenie prieten = (Prietenie) o;
        return get_prieten_1().equals(prieten.get_prieten_1()) && get_prieten_2().equals(prieten.get_prieten_2());
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id_prieten_1, id_prieten_2);
    }

}

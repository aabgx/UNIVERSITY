package domain;

import java.util.Objects;

public class Prietenie extends Entitate<String>{
    private final String idPrieten1,idPrieten2;

    /***
     * constructor
     * @param id - id prietenie
     * @param idPrieten1 - id prieten 1
     * @param idPrieten2 -  id prieten 2
     */
    public Prietenie(String id,String idPrieten1, String idPrieten2){
        super(id);
        this.idPrieten1=idPrieten1;
        this.idPrieten2=idPrieten2;
    }


    /***
     * Metoda suprascrisa toString
     * @param o - cel cu care comapram
     * @return true daca sunt egale, false altfel
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Prietenie prietenie = (Prietenie) o;
        return Objects.equals(idPrieten1, prietenie.idPrieten1) && Objects.equals(idPrieten2, prietenie.idPrieten2);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idPrieten1, idPrieten2);
    }

    /***
     * Metoda suprascrisa toString
     * @return stringul corespunzator entitatii
     */
    @Override
    public String toString() {
        return "Prietenie{" + "id="+id +"id1='" + idPrieten1 + '\'' + ", id2='" + idPrieten2 + '\'' + '}';
    }


    /***
     * Getter id prieten 1 (settere nu am ca s finale)
     * @return id prieten 1
     */
    public String getIdPrieten1() {
        return idPrieten1;
    }
    /***
     * Getter id prieten 2 (settere nu am ca s finale)
     * @return id prieten 2
     */
    public String getIdPrieten2() {
        return idPrieten2;
    }

}

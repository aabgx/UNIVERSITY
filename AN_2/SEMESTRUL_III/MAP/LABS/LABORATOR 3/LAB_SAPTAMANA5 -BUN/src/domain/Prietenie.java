package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Prietenie extends Entitate<String>{
    private final String idPrieten1,idPrieten2;
    private LocalDateTime data;

    /***
     * constructor
     * @param id - id prietenie
     * @param idPrieten1 - id prieten 1
     * @param idPrieten2 -  id prieten 2
     */
    public Prietenie(String id,String idPrieten1, String idPrieten2, LocalDateTime data){
        super(id);
        this.idPrieten1=idPrieten1;
        this.idPrieten2=idPrieten2;
        this.data=data;
    }


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dataFormatata=data.format(formatter);
        return "Prietenie{" +
                "idPrieten1='" + idPrieten1 + '\'' +
                ", idPrieten2='" + idPrieten2 + '\'' +
                ", data=" + dataFormatata +
                ", id=" + id +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Prietenie prietenie = (Prietenie) o;
        return Objects.equals(idPrieten1, prietenie.idPrieten1) && Objects.equals(idPrieten2, prietenie.idPrieten2) && Objects.equals(data, prietenie.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idPrieten1, idPrieten2, data);
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

    public LocalDateTime getData() {return data;}

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}

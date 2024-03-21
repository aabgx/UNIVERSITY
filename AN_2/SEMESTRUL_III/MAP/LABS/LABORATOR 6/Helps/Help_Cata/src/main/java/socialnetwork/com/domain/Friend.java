package socialnetwork.com.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Friend extends Entity<Integer>{
    private Integer idPrieten1, idPrieten2;
    private Boolean pending = true;

    private LocalDateTime data;

    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return getPrieten1()+" & "+ getPrieten2()+" at "+ getData().format(formatter);
    }


    /**
     * Constructorul obiectului
     *
     * @param idPrieten1 - id-ul primului prieten din prietenie
     * @param idPrieten2 - id-ul celui de-al doilea prieten
     */
    public Friend(Integer idPrieten1, Integer idPrieten2) {
        super(null);
        this.idPrieten1 = idPrieten1;
        this.idPrieten2 = idPrieten2;
        this.data=LocalDateTime.now();
    }
    public Friend(Integer idPrieten1, Integer idPrieten2, LocalDateTime data){
        super(null);
        this.idPrieten1 = idPrieten1;
        this.idPrieten2 = idPrieten2;
        this.data=data;
    }
    public Friend(Integer idPrieten1, Integer idPrieten2, LocalDateTime data, Boolean pending){
        super(null);
        this.idPrieten1 = idPrieten1;
        this.idPrieten2 = idPrieten2;
        this.data=data;
        this.pending = pending;
    }
    public Friend(Integer idPrieten1, Integer idPrieten2, Boolean pending){
        super(null);
        this.idPrieten1 = idPrieten1;
        this.idPrieten2 = idPrieten2;
        this.data=LocalDateTime.now();
        this.pending = pending;
    }

    /***
     * Returneaza id-ul primului prieten din obiect
     * @return Id-ul primului prieten
     */
    public Integer getPrieten1() {
        return idPrieten1;
    }

    /***
     * Returneaza id-ul celui de-al doilea prieten
     * @return Id-ul celui de-al doilea prieten
     */
    public Integer getPrieten2(){
        return idPrieten2;
    }

    public LocalDateTime getData(){
        return data;
    }

    public Boolean getPending(){return pending;}

    public void setPending(Boolean pending){
        this.pending = pending;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friend prieten = (Friend) o;
        return getPrieten1().equals(prieten.getPrieten1()) && getPrieten2().equals(prieten.getPrieten2()) && getPending().equals(prieten.getPending());
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idPrieten1, idPrieten2,pending);
    }

}

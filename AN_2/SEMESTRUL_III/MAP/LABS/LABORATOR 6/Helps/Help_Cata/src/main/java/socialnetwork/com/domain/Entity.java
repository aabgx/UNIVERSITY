package socialnetwork.com.domain;

import java.util.Random;

public abstract class Entity<ID> {
    private Integer id;
    Random rn = new Random();
    public Entity(Integer id) {
        if(id == null) this.id = rn.nextInt();
        else{
        this.id=id;}
    }

    public Integer get_id() {
        return id;
    }
    public void set_id(Integer id){
        this.id=id;
    }

}

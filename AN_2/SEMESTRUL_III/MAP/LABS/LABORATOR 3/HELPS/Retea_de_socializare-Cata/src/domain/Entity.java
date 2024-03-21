package domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class Entity<ID> implements Serializable {
    private ID id;

    public Entity(ID id) {
        if(id == null) {
            this.id = (ID) UUID.randomUUID().toString();
        }
        else{
        this.id=id;}
    }

    public ID get_id() {
        return id;
    }
    public void set_id(ID id){
        this.id=id;
    }
}

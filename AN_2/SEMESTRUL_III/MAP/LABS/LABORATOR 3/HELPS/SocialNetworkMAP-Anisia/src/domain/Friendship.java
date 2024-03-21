package domain;

import java.util.Objects;

public class Friendship extends Entity<Pair<Long, Long>>{
    public Friendship(Long u1, Long u2) {
        super.setId(new Pair<Long, Long>(u1, u2));
    }

    /**
     * Defines equality between 2 friendships
     * Friendship equality condition: have the same attributes (first and second) in pair
     * @return int, hashCode generated based on first and second attributes
     */
    @Override
    public boolean equals(Object o) {
       if (this==o) return true;
       if(o == null || getClass() != o.getClass()) return false;
       return Objects.equals(getId().getFirst(), ((Friendship) o).getId().getFirst())&&
               Objects.equals(getId().getSecond(), ((Friendship) o).getId().getSecond());
    }

    /**
     * Overrides hashCode method in Objects class
     * @return int, generates hash code based on id
     */
    @Override
    public int hashCode(){
        return Objects.hash(getId().getFirst(), getId().getSecond());
    }

    /**
     * Overrides method toString in Objects class
     * @return String, string reperesentation of Friendship
     */
    @Override
    public String toString() {
        return "Friendship{ " +
                "Id user1='"+this.getId().getFirst()+'\''+
                ", Id user2='"+this.getId().getSecond()+'\''+
                "}";
    }
}

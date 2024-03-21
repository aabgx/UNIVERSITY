package com.example.socialnetworkgui.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.example.socialnetworkgui.utils.Constants.DATE_TIME_FORMATTER;

public class Friendship extends Entity<Pair<Long, Long>>{
    private LocalDateTime friendsFrom;

    public Friendship(Long u1, Long u2, LocalDateTime friendsFrom) {
        super.setId(new Pair<Long, Long>(u1, u2));
        this.friendsFrom= friendsFrom;
    }

    public LocalDateTime getDate(){
        return this.friendsFrom;
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
                ", Date='"+this.friendsFrom.format(DATE_TIME_FORMATTER)+'\''+
                "}";
    }
}

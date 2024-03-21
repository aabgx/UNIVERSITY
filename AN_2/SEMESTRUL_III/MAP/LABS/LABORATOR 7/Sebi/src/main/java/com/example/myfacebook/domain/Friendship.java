package com.example.myfacebook.domain;

public class Friendship extends Entity<Integer> {
    //    private final User u1;
//    private final User u2;
    public int u1;
    public int u2;
    //private int friendshipID;
    public String username1;
    public String username2;
    public String friendsFrom;


    public Friendship(int u1, int u2, String friendsFrom) {
        this.u1 = u1;
        this.u2 = u2;
        this.username1=
        this.friendsFrom = friendsFrom;
    }

    public Friendship(int u1, int u2, String friendsFrom, String username1, String username2){
        this(u1, u2, friendsFrom);
        this.username1=username1;
        this.username2=username2;
    }
    public Integer getU1() {
        return u1;
    }

    public Integer getU2() {
        return u2;
    }

    public Integer getIdFr() {
        return getId();
    }

    public void setId(int id){
        super.setId(id);
    }
    public String getFriendsFrom() {
        return friendsFrom;
    }

    public void setU1(int u1) {
        this.u1 = u1;
    }

    public void setU2(int u2) {
        this.u2 = u2;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public void setFriendsFrom(String friendsFrom) {
        this.friendsFrom = friendsFrom;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }
}

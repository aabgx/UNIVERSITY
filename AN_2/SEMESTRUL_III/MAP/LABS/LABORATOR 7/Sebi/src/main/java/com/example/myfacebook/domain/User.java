package com.example.myfacebook.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User extends Entity<Integer> {
    //private ID userID;
    //private int userID;
    public String username;
    public int age;
    public String sex;
    public String city;
    public String password;

    List<User> friends;

    public User(int userID, String username, int age, String sex, String city, String password) {
        //this.userID = userID;
        setId(userID);
        this.username = username;
        this.age = age;
        this.sex = sex;
        this.city = city;
        this.password = password;
        this.friends=new ArrayList<>();
    }

    public Integer getUserID() {
        return getId();
    }

    public void addFriend(User usr){
        friends.add(usr);
    }
    public void removeFriend(User usr){
        friends.remove(usr);
    }
    /*public ID getUserID2() {
        return new ID(userID);
    }*/

    public String getUsername() {
        return username;
    }

    /*public void setUserID(ID userID) {
        this.userID = userID;
    }*/

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), username);
    }


    @Override
    public String toString() {
        return "User(" +
                "ID:" + getId() +
                ", username:'" + username + '\'' +
                ", age:" + age +
                ", sex:'" + sex + '\'' +
                ", city:'" + city + '\'' +
                ')';
    }

    public List<User> getFriends(){
        return friends;
    }


}

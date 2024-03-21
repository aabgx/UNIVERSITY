package com.example.lab6.domain;
import java.util.ArrayList;
import java.util.List;

public class Community {
    List<User> persoane = new ArrayList<>();
    List<Friendship> prieteni = new ArrayList<>();
    public Community(List<User> persoane, List<Friendship> prieteni)
    {
        this.persoane.addAll(persoane);
        this.prieteni.addAll(prieteni);
    }
    @Override
    public String toString() {
        String toString1 = "Community:\n     User:\n";
        for(User e:this.persoane)
        {
            String nou = e.toString();
            toString1 = toString1 + nou;
        }
        toString1 = toString1 + "\n"+"     Prieteni: \n";
        int id=1;
        if(prieteni.size()==0)
        {
            toString1 = toString1 + "  Nu exista prieteni!";
        }
        for(Friendship f:this.prieteni)
        {
            String nou = "  "+id+"." + f.getU1() + "    " + f.getU2();
            toString1 = toString1+nou;
            id++;
        }
        return toString1;
    }
}

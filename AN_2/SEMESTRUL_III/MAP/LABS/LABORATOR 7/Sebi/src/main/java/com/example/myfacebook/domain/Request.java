package com.example.myfacebook.domain;

public class Request extends Entity<Integer>{
    private Integer from;
    private Integer to;

    public Request(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setId(int id){
        super.setId(id);
    }

    public Integer getIdRe(){
        return getId();
    }
}

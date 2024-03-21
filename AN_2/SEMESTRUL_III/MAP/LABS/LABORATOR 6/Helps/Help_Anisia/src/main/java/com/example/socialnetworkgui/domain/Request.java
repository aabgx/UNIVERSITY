package com.example.socialnetworkgui.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.example.socialnetworkgui.utils.Constants.DATE_TIME_FORMATTER;

public class Request extends Entity<Pair<Long, Long>>{
    private LocalDateTime sentAt;
    private RequestStatus status;

    public Request(Long u1, Long u2, LocalDateTime sentAt, RequestStatus status) {
        super.setId(new Pair<Long, Long>(u1,u2));
        this.sentAt = sentAt;
        this.status = status;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public RequestStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o){
        if (this==o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        return Objects.equals(getId().getFirst(), ((Request) o).getId().getFirst())&&
                Objects.equals(getId().getSecond(), ((Request) o).getId().getSecond());
    }

    @Override
    public int hashCode(){
        return Objects.hash(getId().getFirst(), getId().getSecond());
    }

    @Override
    public String toString() {
        return "Request{" +
                "Id user1='"+this.getId().getFirst()+'\''+
                ", Id user2='"+this.getId().getSecond()+'\''+
                "sentAt=" + sentAt.format(DATE_TIME_FORMATTER) +
                ", status=" + status +
                '}';
    }
}

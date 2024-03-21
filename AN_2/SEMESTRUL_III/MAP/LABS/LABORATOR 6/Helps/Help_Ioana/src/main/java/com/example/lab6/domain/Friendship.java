package com.example.lab6.domain;
import java.time.LocalDateTime;
import java.util.Objects;

public class Friendship extends Entity<Pair<Integer>> {
    User u1, u2;
    private LocalDateTime data;
    private boolean acceptat;
    public Friendship(User u1, User u2, LocalDateTime data) {
        this.u1 = u1;
        this.u2 = u2;
        this.data = data;
        this.acceptat=false;
    }
    @Override
    public String toString() {
        return this.getU1().toString() + this.getU2().toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return Objects.equals(this.getId(),that.getId());
    }

    public boolean isAcceptat() {
        return acceptat;
    }

    public void setAcceptat(boolean acceptat) {
        this.acceptat = acceptat;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        return Objects.hash(u1, u2);
    }
    public User getU1() {
        return u1;
    }
    public User getU2() {
        return u2;
    }
}

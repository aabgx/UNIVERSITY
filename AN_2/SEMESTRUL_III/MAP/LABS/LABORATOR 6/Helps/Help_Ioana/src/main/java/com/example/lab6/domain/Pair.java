package com.example.lab6.domain;

import java.util.Objects;

public class Pair<Tip> {
    Tip id1;
    Tip id2;

    public Pair(Tip id1, Tip id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    @Override
    public String toString() {
        return  id1 +";"+id2;
    }

    public Tip getId1() {
        return id1;
    }
    public Tip getId2() {
        return id2;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?> pair = (Pair<?>) o;
        return Objects.equals(id1, pair.id1) && Objects.equals(id2, pair.id2) || Objects.equals(id1, pair.id2) && Objects.equals(id2, pair.id1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id1, id2);
    }
}

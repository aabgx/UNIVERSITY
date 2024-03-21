package com.example.socialnetworkgui.domain;

import java.util.Objects;

/**
 * Define Pair class that holds two objects e1 and e2
 * Used for creating Friendship class with ID that is a Pair object
 * @param <E1>, type of first object
 * @param <E2>, type of second object
 */

public class Pair<E1, E2> {
    private E1 e1;
    private E2 e2;

    public Pair(E1 e1, E2 e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public E1 getFirst() {
        return e1;
    }

    public E2 getSecond() {
        return e2;
    }

    public void setFirst(E1 e1) {
        this.e1 = e1;
    }

    public void setSecond(E2 e2) {
        this.e2 = e2;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "e1=" + e1 +
                ", e2=" + e2 +
                '}';
    }

    /**
     * Defines equality between 2 pairs
     * 2 pairs are equal if they hold the same attributes in 'first=e1' and 'second=e2' fields
     * (order does not matter)!
     * @param o, Object, pair to which current pair is compared
     * @return boolean, True if pairs are considered to be equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return (Objects.equals(e1, pair.e1) && Objects.equals(e2, pair.e2))||
                (Objects.equals(e1, pair.e2) && Objects.equals(e2, pair.e1));
    }

    //Symmetric hashCode!!!!!!!!!!!!!!!!!!!!!

    /**
     * Returns symmetric hash code for 'symmetric' pairs
     * ex. Pair(1,2) and Pair(2,1) would get the same hashcode
     * @return int, generated hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(e1, e2)+Objects.hash(e2,e1);
    }
}

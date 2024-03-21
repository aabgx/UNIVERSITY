package com.example.socialnetwork.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class Friendship extends Entity<Long> {
    private Long left;
    private Long right;
    private String status;
    private LocalDateTime friendsFrom;
    private Set<Long> ids;

    public Friendship(Long id1, Long id2, LocalDateTime friendsFrom) {
        this.left = id1;
        this.right = id2;
        this.status = "pending";
        this.friendsFrom = friendsFrom;
        this.ids = Set.of(id1, id2);
    }

    public Friendship(Long id1, Long id2, String status, LocalDateTime friendsFrom) {
        this.left = id1;
        this.right = id2;
        this.status = status;
        this.friendsFrom = friendsFrom;
        this.ids = Set.of(id1, id2);
    }

    public Long getLeft() {
        return left;
    }

    public void setLeft(Long id1) {
        this.left = id1;
    }

    public Long getRight() {
        return right;
    }

    public void setRight(Long id2) {
        this.right = id2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getFriendsFrom() {
        return friendsFrom;
    }

    public void setFriendsFrom(LocalDateTime friendsFrom) {
        this.friendsFrom = friendsFrom;
    }

    public Set<Long> getIds() {
        return ids;
    }

    public void setIds(Set<Long> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "Prietenie{" +
                "id1=" + left +
                ", id2=" + right +
                ", status='" + status + '\'' +
                ", friendsFrom=" + friendsFrom +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Friendship that)) return false;
        return getIds().equals(that.getIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIds());
    }
}

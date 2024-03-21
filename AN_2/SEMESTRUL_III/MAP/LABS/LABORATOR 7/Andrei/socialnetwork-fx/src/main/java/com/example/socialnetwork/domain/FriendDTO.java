package com.example.socialnetwork.domain;

public class FriendDTO {
    private String firstName;
    private String lastName;
    private String status;
    private String date;
    private Long id;
    private Long friendId;

    public FriendDTO(String firstName, String lastName, String status, String date, Long id, Long friendId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.date = date;
        this.id = id;
        this.friendId = friendId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public Long getId() {
        return id;
    }

    public Long getFriendId() {
        return friendId;
    }

    @Override
    public String toString() {
        return "FriendDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status='" + status + '\'';
    }
}

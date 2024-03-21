package com.example.socialnetworkgui.domain;

public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private String sentAt;
    private String status;

    public UserRequestDTO(String firstName, String lastName, String sentAt, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sentAt = sentAt;
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

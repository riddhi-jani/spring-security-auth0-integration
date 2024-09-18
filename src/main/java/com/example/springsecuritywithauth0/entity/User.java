package com.example.springsecuritywithauth0.entity;

public class User {
    String email;
    String connection;
    String password;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getConnection() {
        return connection;
    }
    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

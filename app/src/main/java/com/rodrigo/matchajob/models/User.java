package com.rodrigo.matchajob.models;

/**
 * Created by rodrigo on 8/31/2016.
 */
public class User {
    private int Id;
    private String Email;
    private String Password;

    public User() {
    }

    public User(int id, String email, String password) {
        this.Id = id;
        this.Email = email;
        this.Password = password;
    }

    public User(String email, String password) {
        Email = email;
        Password = password;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
}

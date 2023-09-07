package com.example.kwesijames;

public class users {

    private int id;
    private String Firstname;
    private String Lastname;
    private String Username;
    private String Password;
    private int admin;

    public users(int id, String firstname, String lastname, String username, String password, int admin) {
        this.id = id;
        this.Firstname = firstname;
        this.Lastname = lastname;
        this.Username = username;
        this.Password = password;
        this.admin = admin;
    }

    public users(String firstname, String lastname, String username, String password) {
        this.Firstname = firstname;
        this.Lastname = lastname;
        this.Username = username;
        this.Password = password;
    }

    public users(int id, String username, String firstname, String lastname, String password) {
        this.id = id;
        this.Username = username;
        this.Firstname = firstname;
        this.Lastname = lastname;
        this. Password = password;
    }

    public users(String username, String password) {
        Username = username;
        Password = password;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getFirstname() {return Firstname;}

    public void setFirstname(String firstname) {Firstname = firstname;}

    public String getLastname() {return Lastname;}

    public void setLastname(String lastname) {Lastname = lastname;}

    public String getUsername() {return Username;}

    public void setUsername(String username) {Username = username;}

    public String getPassword() {return Password;}

    public void setPassword(String password) {Password = password;}

    public int getAdmin() {return admin;}

    public void setAdmin(int admin) {this.admin = admin;}

}

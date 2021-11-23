package com.example.deliverroot;

public class UserClass {
    String username, email,password,phoneno;

    public UserClass() {
    }

    public UserClass(String username, String email, String password,String phoneno) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneno = phoneno;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

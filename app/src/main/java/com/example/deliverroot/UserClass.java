package com.example.deliverroot;

public class UserClass {
    String username, email,password,phoneno,pimage;

    public UserClass() {
    }

    public String getPimage() {
        return pimage;
    }

    public UserClass(String username, String email, String password, String phoneno) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneno = phoneno;
        this.pimage="https://images.unsplash.com/photo-1546587348-d12660c30c50?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8bmF0dXJhbHxlbnwwfHwwfHw%3D&w=1000&q=80";
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

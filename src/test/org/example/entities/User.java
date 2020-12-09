package org.example.entities;

import org.example.data.RandomData;

public class User {
    private String username;
    private String password;
    private String email;
    private int age;


    public User(){
        username = RandomData.generateString();
        password = RandomData.generatePass(8);
        age = RandomData.generateAge();
    }

    public User(String username, String password, String email, int age) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
    }

    @Override
    public String toString() {
        return username + "," + password  + "," + email  + "," + age;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }
}

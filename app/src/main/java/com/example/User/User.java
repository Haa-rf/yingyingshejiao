package com.example.User;

/**
 * Created by ScoutB on 2018/3/12.
 */

public class User {
    private String username;
    private String pwd;
    private int id;
    public User(){
        return;
    }

    public User(String username, String pwd){
        this.username = username;
        this.pwd = pwd;
    }

    public User(String username, String pwd, int id){
        this.username = username;
        this.pwd = pwd;
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public String getPwd(){
        return pwd;
    }

    public int getId(){
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPwd(String pwd){
        this.pwd=pwd;
    }

    public void setId(int id){
        this.id = id;
    }
}

package com.example.JavaBean;

/**
 * Created by ScoutB on 2018/3/12.
 */

public class User {
    private String user_name;
    private String user_nickname;
    private String user_image;
    private String user_sex="男";
    private String user_sign="";

    public User(){}

    public User(String user_name, String user_nickname, String user_image, String user_sex){
        this.user_name=user_name;
        this.user_nickname=user_nickname;
        this.user_image=user_image;
        if(user_sex.equals("男") || user_sex.equals("女")) {
            this.user_sex = user_sex;
        }
    }

    public User(String user_name, String user_nickname, String user_image, String user_sex, String user_sign){
        this.user_name=user_name;
        this.user_nickname=user_nickname;
        this.user_image=user_image;
        if(user_sex.equals("男") || user_sex.equals("女")) {
            this.user_sex = user_sex;
        }
        this.user_sign=user_sign;
    }

    public String getUser_name(){
        return user_name;
    }

    public String getUser_nickname(){
        return this.user_nickname;
    }

    public String getUser_image(){
        return this.user_image;
    }

    public String getUser_sex(){
        return this.user_sex;
    }

    public String getUser_sign(){
        return user_sign;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_nickname(String user_nickname){
        this.user_nickname = user_nickname;
    }

    public void setUser_image(String user_image){
        this.user_image=user_image;
    }

    public void setUser_sex(String user_sex){
        if(user_sex.equals("男") || user_sex.equals("女")){
            this.user_sex=user_sex;
        }
    }
    public void setUser_sign(String user_sign){
        this.user_sign=user_sign;
    }
}

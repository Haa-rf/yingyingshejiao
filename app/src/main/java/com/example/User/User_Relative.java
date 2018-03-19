package com.example.User;

/**
 * Created by ScoutB on 2018/3/18.
 */

public class User_Relative {
    private String user_friendname;
    private String user_friendgroup;

    public User_Relative(String user_friendname, String user_friendgroup){
        this.user_friendname=user_friendname;
        this.user_friendgroup=user_friendgroup;
    }

    public String getUser_friendname(){
        return user_friendname;
    }

    public String getUser_friendgroup(){
        return user_friendgroup;
    }

    public void setUser_friendname(String user_friendname){
        this.user_friendname=user_friendname;
    }

    public void setUser_friendgroup(String user_friendgroup){
        this.user_friendgroup=user_friendgroup;
    }
}

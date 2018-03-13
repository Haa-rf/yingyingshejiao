package com.example.User;

/**
 * Created by ScoutB on 2018/3/12.
 */

public class PrivilegedUser extends User {
    protected short Privilege;

    public PrivilegedUser(){
        return;
    }

    public PrivilegedUser(String username, String pwd) {
        super(username, pwd);
    }

    public PrivilegedUser(String username, String pwd, short Privilege){
        super(username, pwd);
        this.Privilege = Privilege;
    }
    void getPrivilege(short Privilege){
        this.Privilege=Privilege;
    }
}

package com.example.RegisterAndLogin;

import android.content.Context;
import android.util.Log;

import com.example.DBM.DBMUtil;
import com.example.User.PrivilegedUser;
import com.example.User.User;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by ScoutB on 2018/3/13.
 */

public class Register {
    private String TAG = "RegTAG";
    private User Ruser = new User();
    private PrivilegedUser Privuser = new PrivilegedUser();
    public Register(User user){
        this.Ruser = user;
    }

    public Register(PrivilegedUser puser){
        this.Privuser = puser;
    }

    private void RegisterMain(String username, String pwd, Context context){
        User user = new User();
        user.setUsername(username);
        user.setPwd(pwd);
        try{
            DBMUtil.add(context, user);
            EMClient.getInstance().createAccount(username, pwd);
        } catch (HyphenateException e) {
            Log.i(TAG, "Failed Register");
        }
    }
}

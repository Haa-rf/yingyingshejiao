package com.example.RegisterAndLogin;

import android.content.Context;
import android.util.Log;

import com.example.DBM.DBMUtil;
import com.example.User.User;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by ScoutB on 2018/3/13.
 */

public class Register {
    private String TAG = "RegTAG";
    private User Ruser = new User();
    public Register(User user){
        this.Ruser = user;
    }

    private void RegisterMain(Context context, User user, String pwd){
        try{
            DBMUtil.addUser(context, user);
            EMClient.getInstance().createAccount(user.getUser_name(), pwd);
        } catch (HyphenateException e) {
            Log.i(TAG, "Failed Register");
        }
    }
}

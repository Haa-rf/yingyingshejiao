package com.example.RegisterAndLogin;

import android.util.Log;

import com.example.User.User;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * Created by ScoutB on 2018/3/13.
 */

public class Login {
    private static String TAG = "LogTAG";
    private User Luser = new User();
    public Login(User user){
        this.Luser = user;
    }


    private void ClientLogin(String username, String pwd) {

        EMClient.getInstance().login(username, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.i(TAG, "successed login");
            }

            @Override
            public void onError(int i, String s) {
                Log.i(TAG, "Failed login");
            }

            @Override
            public void onProgress(int i, String s) {
                Log.i(TAG, "Plz wait.......");
            }
        });

    }
}

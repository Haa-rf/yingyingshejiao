package com.example.Contacts;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by Administrator on 2018/3/20.
 */

public class MyContactListener implements EMContactListener {
    private Context context;
    public MyContactListener(Context context){
            this.context=context;
    }
    @Override
    public void onContactAdded(String s) {
        Toast.makeText(context,"你已经添加好友："+s,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onContactDeleted(String s) {
        Log.i("TAG", "onContactRefused==>"+s);
        Toast.makeText(context,"你已经被好友"+ s +"删除",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onContactInvited(final String s, String s1) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(context).inflate(R.layout.mydialog, null);
// 设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view);
        final AlertDialog dialog = builder.show();
        final TextView Detail = view.findViewById(R.id.detail);
        final TextView Reason =view.findViewById(R.id.HisReason);
        final Button Agree=view.findViewById(R.id.agree);
        final Button Decline=view.findViewById(R.id.decline);
        Detail.setText(s+"请求加你为好友");
        Reason.setText(s1);
        Agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().contactManager().acceptInvitation(s);
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        Decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().contactManager().declineInvitation(s);
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


    }

    @Override
    public void onFriendRequestAccepted(String s) {

        Toast.makeText(context,"同意了你的好友请求",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFriendRequestDeclined(String s) {
        Toast.makeText(context,"拒绝了你的好友请求",Toast.LENGTH_SHORT).show();
    }
}

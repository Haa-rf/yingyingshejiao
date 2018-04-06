package com.example.Contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

public class JoinGroupActivity extends AppCompatActivity {
    TextView GroupName;
    TextView GroupDesc;
    EMGroup GroupJoined;
    Button joinGroup;
    String GroupNameJoined;
    String GroupIdJoined;
    String GroupDescJoined;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);
        Intent Group =getIntent();
        Bundle GroupB=Group.getExtras();
        GroupIdJoined=GroupB.getString("GroupId");
        new Thread(new Runnable() {
            @Override
            public void run() {
                getGroupJoined();
            }
        }).start();
        GroupName=findViewById(R.id.GroupName);
        GroupDesc=findViewById(R.id.GroupDesc);
        joinGroup=findViewById(R.id.JoinGroupButton);
        GroupName.setText(GroupJoined.getGroupName());
        GroupDesc.setText(GroupJoined.getDescription());
        joinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        confirmJoinGroup();
                        finish();
                    }
                }).start();
            }
        });


    }

    public void getGroupJoined(){
        try {
            GroupJoined= EMClient.getInstance().groupManager().getGroupFromServer(GroupIdJoined);
        } catch (HyphenateException e) {
            e.printStackTrace();
            Toast.makeText(JoinGroupActivity.this,"获取群信息失败",Toast.LENGTH_SHORT).show();
        }
    }

    public void confirmJoinGroup(){
        try {
            EMClient.getInstance().groupManager().joinGroup(GroupJoined.getGroupId());
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }
}

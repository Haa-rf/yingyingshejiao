package com.yingyingshejiao.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yingyingshejiao.R;

/**
 * Created by apple on 18/03/2018.
 */

public class ChatActivity extends AppCompatActivity{
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent=getIntent();
        ChatFragment chatFragment=new ChatFragment();

        chatFragment.setArguments(intent.getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.layout_container,chatFragment).commit();

    }
}

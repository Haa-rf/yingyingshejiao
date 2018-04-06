package com.yingyingshejiao.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yingyingshejiao.R;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;

/**
 * Created by apple on 18/03/2018.
 */

public class ChatTestActivity extends AppCompatActivity {
    private Button solo_button;
    private Button group_button;
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_testchat);
        init();
    }
    private void init(){
        solo_button=findViewById(R.id.solo_chat);
        group_button=findViewById(R.id.group_chat);
        solo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solochat();
            }
        });
        group_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupchat();
            }
        });
    }
    private void solochat(){
        Intent intent = new Intent(ChatTestActivity.this,ChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_USER_ID,"user2");
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
        startActivity(intent);
    }
    private void groupchat(){
        Intent intent = new Intent(ChatTestActivity.this,ChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_USER_ID,"group1");
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.GroupChat);
        startActivity(intent);
    }
}

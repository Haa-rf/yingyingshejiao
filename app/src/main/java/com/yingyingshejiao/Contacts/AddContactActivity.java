package com.yingyingshejiao.Contacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yingyingshejiao.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import static android.widget.Toast.LENGTH_SHORT;

public class AddContactActivity extends AppCompatActivity {
    Button sendAddMessage;
    Button Back;
    EditText userId;
    EditText reasonOfAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        init();
    }

    public void init(){
        Back=findViewById(R.id.back);
        sendAddMessage=findViewById(R.id.sendAddMessage);
        userId=findViewById(R.id.EditContactId);
        reasonOfAdd=findViewById(R.id.EditReason);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * 添加好友
         */
        sendAddMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().contactManager().addContact(userId.getText().toString(), reasonOfAdd.getText().toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AddContactActivity.this,"添加成功",LENGTH_SHORT).show();
                                    finish();
                                }
                            });

                        } catch (HyphenateException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AddContactActivity.this,"添加失败",LENGTH_SHORT).show();
                                    finish();
                                }
                            });

                        }
                    }
                }).start();
            }
        });
    }
}

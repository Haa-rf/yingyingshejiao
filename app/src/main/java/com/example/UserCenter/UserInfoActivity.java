package com.example.UserCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.Activity.MainActivity;
import com.example.DBM.DBMUtil;
import com.example.JavaBean.User;
import com.example.R;
import com.hyphenate.chat.EMClient;

public class UserInfoActivity extends AppCompatActivity {

    private ImageView head;
    private EditText nickname;
    private EditText username;
    private RadioGroup sex;
    private EditText sign;
    private User Me;
    private String user_name;
    private String nick_name;
    private String user_image;
    private String user_sex;
    private String user_sign;
    public  int count;
    private int pic_num =8 ;
    private TextView finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
    }

    private void initView(){
        head = findViewById(R.id.image_user);
        //nickname = findViewById(R.id.name_editText);
        username = findViewById(R.id.id_editText);
        sex = findViewById(R.id.radioGroup);
        //sign = findViewById(R.id.sign_editText);
        finish = findViewById(R.id.finish);

        user_name = EMClient.getInstance().getCurrentUser();
        username.setText(user_name);
        Me = DBMUtil.query(this,user_name);
        head.setImageResource(Integer.parseInt(Me.getUser_image()));
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (count%pic_num == 0){
                    head.setImageResource(R.drawable.head_image_m1);
                    user_image = Integer.toString(R.drawable.head_image_m1);
                    Me.setUser_image(user_image);
                }
                if (count%pic_num == 1){
                    head.setImageResource(R.drawable.head_image_f1);
                    user_image = Integer.toString(R.drawable.head_image_f1);
                    Me.setUser_image(user_image);
                }
                if (count%pic_num == 2){
                    head.setImageResource(R.drawable.head_image_m2);
                    user_image = Integer.toString(R.drawable.head_image_m2);
                    Me.setUser_image(user_image);
                }
                if (count%pic_num == 3){
                    head.setImageResource(R.drawable.head_image_f2);
                    user_image = Integer.toString(R.drawable.head_image_f2);
                    Me.setUser_image(user_image);
                }
                if (count%pic_num == 4){
                    head.setImageResource(R.drawable.head_image_m3);
                    user_image = Integer.toString(R.drawable.head_image_m3);
                    Me.setUser_image(user_image);
                }
                if (count%pic_num == 5){
                    head.setImageResource(R.drawable.head_image_f3);
                    user_image = Integer.toString(R.drawable.head_image_f3);
                    Me.setUser_image(user_image);
                }
                if (count%pic_num == 6){
                    head.setImageResource(R.drawable.head_image_m4);
                    user_image = Integer.toString(R.drawable.head_image_m4);
                    Me.setUser_image(user_image);
                }
                if (count%pic_num == 7){
                    head.setImageResource(R.drawable.head_image_f4);
                    user_image = Integer.toString(R.drawable.head_image_f4);
                    Me.setUser_image(user_image);

                }
                count++;
            }
        });

        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.male) {
                    user_sex = "男";
                    Me.setUser_sex(user_sex);
                } else if (i == R.id.female) {
                    user_sex = "女";
                    Me.setUser_sex(user_sex);
                }
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nickname = findViewById(R.id.name_editText);
                sign = findViewById(R.id.sign_editText);
                nick_name = nickname.getText().toString().trim();
                Me.setUser_nickname(nick_name);
                user_sign = sign.getText().toString().trim();
                Me.setUser_sign(user_sign);
                DBMUtil.update(UserInfoActivity.this,Me);
                Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

package com.example.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.R;
import com.example.RegisterAndLogin.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
    }
    private  void init(){
        final Intent intent=new Intent(StartActivity.this,LoginActivity.class);
        final Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        };timer.schedule(task,1000*5);
    }
}

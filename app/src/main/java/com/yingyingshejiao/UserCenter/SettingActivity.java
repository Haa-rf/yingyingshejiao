package com.yingyingshejiao.UserCenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yingyingshejiao.PreferenceManager;
import com.yingyingshejiao.R;
import com.yingyingshejiao.RegisterAndLogin.LoginActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.widget.EaseSwitchButton;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * new message notification
     */
    private RelativeLayout rl_switch_notification;
    /**
     * sound
     */
    private RelativeLayout rl_switch_sound;
    /**
     * vibration
     */
    private RelativeLayout rl_switch_vibrate;
    /**
     * speaker
     */
    private RelativeLayout rl_switch_speaker;


    /**
     * line between sound and vibration
     */
    private TextView textview1, textview2;

    private LinearLayout blacklistContainer;


    /**
     * logout
     */
    private Button logoutBtn;


    private RelativeLayout rl_switch_delete_msg_when_exit_group;
    RelativeLayout rl_push_settings;
    private LinearLayout   ll_call_option;

    /**
     * Diagnose
     */
    /**
     * display name for APNs
     */

    private EaseSwitchButton notifySwitch;
    private EaseSwitchButton soundSwitch;
    private EaseSwitchButton vibrateSwitch;
    private EaseSwitchButton speakerSwitch;
    private EaseSwitchButton switch_delete_msg_when_exit_group;
    private EMOptions chatOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    private void init(){
        rl_switch_notification = (RelativeLayout) findViewById(R.id.rl_switch_notification);
        rl_switch_sound = (RelativeLayout) findViewById(R.id.rl_switch_sound);
        rl_switch_vibrate = (RelativeLayout) findViewById(R.id.rl_switch_vibrate);
        rl_switch_speaker = (RelativeLayout) findViewById(R.id.rl_switch_speaker);
        rl_switch_delete_msg_when_exit_group = (RelativeLayout) findViewById(R.id.rl_switch_delete_msg_when_exit_group);
        rl_push_settings = (RelativeLayout)findViewById(R.id.rl_push_settings);

        ll_call_option = (LinearLayout) findViewById(R.id.ll_call_option);


        notifySwitch = (EaseSwitchButton) findViewById(R.id.switch_notification);
        soundSwitch = (EaseSwitchButton) findViewById(R.id.switch_sound);
        vibrateSwitch = (EaseSwitchButton) findViewById(R.id.switch_vibrate);
        speakerSwitch = (EaseSwitchButton) findViewById(R.id.switch_speaker);
        switch_delete_msg_when_exit_group = (EaseSwitchButton)findViewById(R.id.switch_delete_msg_when_exit_group);

        logoutBtn = (Button) findViewById(R.id.btn_logout);
        if(!TextUtils.isEmpty(EMClient.getInstance().getCurrentUser())){
            logoutBtn.setText(getString(R.string.button_logout) + "(" + EMClient.getInstance().getCurrentUser() + ")");
        }

        textview1 = (TextView) findViewById(R.id.textview1);
        textview2 = (TextView) findViewById(R.id.textview2);
        blacklistContainer = (LinearLayout) findViewById(R.id.ll_black_list);
        chatOptions = EMClient.getInstance().getOptions();

//        //首先获取EMChatOptions。
//
//        //设置是否启用新消息提醒（打开或者关闭消息声音和震动提示）。
//
//        chatOptions.setNotifyBySoundAndVibrate(true); //默认为true 开启新消息提醒
//        //设置是否启用新消息声音提醒。
//
//        Options.setNoticeBySound(true); //默认为true 开启声音提醒
//        //设置是否启用新消息震动提醒。
//
//        Options.setNoticedByVibrate(true); //默认为true 开启震动提醒
//        //设置语音消息播放是否设置为扬声器播放。
//
//        Options.setUseSpeaker(true); //默认为true 开启扬声器播放
//        //设置后台接收新消息时是否通过通知栏提示。
//
//        Options.setShowNotificationInBackgroud(true);//默认为true
//
//        //Options.setAcceptInvitationAlways(false);//默认添加好友时为true，是不需要验证的，改成需要验证为false


        blacklistContainer.setOnClickListener(this);
        rl_switch_sound.setOnClickListener(this);
        rl_switch_vibrate.setOnClickListener(this);
        rl_switch_speaker.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        rl_switch_delete_msg_when_exit_group.setOnClickListener(this);
        rl_push_settings.setOnClickListener(this);
        ll_call_option.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_switch_sound:
                if (soundSwitch.isSwitchOpen()) {
                    soundSwitch.closeSwitch();
                    PreferenceManager.getInstance().setSettingMsgSound(false);
                } else {
                    soundSwitch.openSwitch();
                    PreferenceManager.getInstance().setSettingMsgSound(true);
                }
                break;
            case R.id.rl_switch_vibrate:
                if (vibrateSwitch.isSwitchOpen()) {
                    vibrateSwitch.closeSwitch();
                    PreferenceManager.getInstance().setSettingMsgVibrate(false);
                } else {
                    vibrateSwitch.openSwitch();
                    PreferenceManager.getInstance().setSettingMsgVibrate(true);
                }
                break;
            case R.id.rl_switch_speaker:
                if (speakerSwitch.isSwitchOpen()) {
                    speakerSwitch.closeSwitch();
                    PreferenceManager.getInstance().setSettingMsgSpeaker(false);
                } else {
                    speakerSwitch.openSwitch();
                    PreferenceManager.getInstance().setSettingMsgVibrate(true);
                }
                break;
            case R.id.rl_switch_delete_msg_when_exit_group:
                if(switch_delete_msg_when_exit_group.isSwitchOpen()){
                    switch_delete_msg_when_exit_group.closeSwitch();
                    PreferenceManager.getInstance().setDeleteMessagesAsExitGroup(false);
                    chatOptions.setDeleteMessagesAsExitGroup(false);
                }else{
                    switch_delete_msg_when_exit_group.openSwitch();
                    PreferenceManager.getInstance().setDeleteMessagesAsExitGroup(true);
                    chatOptions.setDeleteMessagesAsExitGroup(true);
                }
                break;
            case R.id.btn_logout:
                logout();
                break;
//            case R.id.ll_black_list:
//                startActivity(new Intent(getActivity(), BlacklistActivity.class));
//                break;
//            case R.id.ll_call_option:
//                startActivity(new Intent(getActivity(), CallOptionActivity.class));
//                break;
//            case R.id.rl_push_settings:
//                startActivity(new Intent(getActivity(), OfflinePushSettingsActivity.class));
//                break;
            default:
                break;
        }
    }
    void logout() {
        final ProgressDialog pd = new ProgressDialog(SettingActivity.this);
        String st = getResources().getString(R.string.Are_logged_out);
        pd.setMessage(st);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        EMClient.getInstance().logout(true,new EMCallBack() {

            @Override
            public void onSuccess() {
                SettingActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        // show login screen
                        SettingActivity.this.finish();
                        startActivity(new Intent(SettingActivity.this, LoginActivity.class));

                    }
                });

            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                SettingActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        pd.dismiss();
                        Toast.makeText(SettingActivity.this, "unbind devicetokens failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}

package com.example.Call;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.WindowManager;

import com.example.Activity.MainActivity;
import com.hyphenate.chat.EMClient;


/**
 * Created by chen on 2018/3/14.
 *
 * 通话界面的父类，做一些音视频通话的通用操作
 */
public class CallActivity extends MainActivity {

    // 震动器
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置通话界面属性，保持屏幕常亮，关闭输入法，以及解锁
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    /**
     * 初始化界面方法，做一些界面的初始化操作
     */
    protected void initView() {

        initCallPushProvider();

        // 初始化振动器
        vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);

        if (CallManager.getInstance().getCallState() == CallManager.CallState.DISCONNECTED) {
            CallManager.getInstance().init(activity);
            // 收到呼叫或者呼叫对方时初始化通话状态监听
            CallManager.getInstance().setCallState(CallManager.CallState.CONNECTING);
            CallManager.getInstance().registerCallStateListener();
            CallManager.getInstance().attemptPlayCallSound();

            // 如果不是对方打来的，就主动呼叫
            if (!CallManager.getInstance().isInComingCall()) {
                CallManager.getInstance().makeCall();
            }
        }
    }

    /**
     * 初始化通话推送提供者
     */
    private void initCallPushProvider() {
        CallPushProvider pushProvider = new CallPushProvider();
        EMClient.getInstance().callManager().setPushProvider(pushProvider);
    }

    /**
     * 挂断通话
     */
    protected void endCall() {
        CallManager.getInstance().endCall();
        onFinish();
    }

    /**
     * 拒绝通话
     */
    protected void rejectCall() {
        CallManager.getInstance().rejectCall();
        onFinish();
    }

    /**
     * 接听通话
     */
    protected void answerCall() {
        CallManager.getInstance().answerCall();
    }

    /**
     * 调用系统振动，触发按钮的震动反馈
     */
    protected void vibrate() {
        vibrator.vibrate(88);
    }

    /**
     * 销毁界面时做一些自己的操作
     */
    protected void onFinish() {
        super.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 通话界面拦截 Back 按键，不能返回
     */
//    @Override public void onBackPressed() {
//        //super.onBackPressed();
//        CallManager.getInstance().addFloatWindow();
//        onFinish();
//    }

    /**
     * 监听通话界面是否a隐藏，处理悬浮窗
     */
//    @Override protected void onUserLeaveHint() {
//        //super.onUserLeaveHint();
//        CallManager.getInstance().addFloatWindow();
//        onFinish();
//    }

    @Override protected void onResume() {
        super.onResume();
        // 判断当前通话状态，如果已经挂断，则关闭通话界面
        if (CallManager.getInstance().getCallState() == CallManager.CallState.DISCONNECTED) {
            onFinish();
            return;
        } else {
            CallManager.getInstance().removeFloatWindow();
        }
    }
}

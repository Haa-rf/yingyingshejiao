package com.example.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Constant;
import com.example.ContactListFragment;
import com.example.R;
import com.example.RegisterAndLogin.LoginActivity;
import com.example.Utils.ChatMessage;
import com.hyphenate.util.EMLog;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected MainActivity activity;

    protected static final String TAG = "MainActivity";
    // textview for unread message count
    private TextView unreadLabel;
    // textview for unread event message
    private TextView unreadAddressLable;

    private Button[] mTabs;
    private ContactListFragment contactListFragment;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;
    // user logged into another device
    public boolean isConflict = false;
    // user account was removed
    private boolean isCurrentAccountRemoved = false;
    private Context context;
    private boolean ckeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                try {
                    //some device doesn't has activity to handle this intent
                    //so add try catch
                    Intent intent = new Intent();
                    intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + packageName));
                    startActivity(intent);
                } catch (Exception e) {
                }
            }
        }
        //make sure activity will not in background if user is logged into another device or removed
//        if (getIntent() != null &&
//                (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false) ||
//                        getIntent().getBooleanExtra(Constant.ACCOUNT_KICKED_BY_CHANGE_PASSWORD, false) ||
//                        getIntent().getBooleanExtra(Constant.ACCOUNT_KICKED_BY_OTHER_DEVICE, false))) {
//            DemoHelper.getInstance().logout(false,null);
//            finish();
//            startActivity(new Intent(this, LoginActivity.class));
//            return;
//        } else if (getIntent() != null && getIntent().getBooleanExtra("isConflict", false)) {
//            finish();
//            startActivity(new Intent(this, LoginActivity.class));
//            return;
//        }
        setContentView(R.layout.activity_main);
        // runtime permission for android 6.0, just require all permissions here for simple
        requestPermissions();

        initView();

    }

    /**
     * 使用AddPermissions包申请权限
     * AddPermissions包支持：Activity，Fragment，SupportFragment(在support库中的)，Context
     * Created by 苟仁福 on 2018/3/14.
     */
    private void requestPermissions(){
        context = this;

        Rationale mRationale = new Rationale() {
            @Override
            public void showRationale(Context context, List<String> permissions,
                                      RequestExecutor executor) {

                // 这里使用一个Dialog询问用户是否继续授权。
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("权限提醒")
                        .setMessage("你已拒绝过该权限，是否接受！")
                        .setPositiveButton("好，给你", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,"你接受了权限获取",Toast.LENGTH_SHORT).show();
                                ckeck = true;
                            }
                        })
                        .setNegativeButton("不，拒绝",  new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,"你拒绝了权限获取",Toast.LENGTH_SHORT).show();
                                ckeck = false;
                            }
                        }).show();

                if (ckeck){
                    // 如果用户继续：
                    executor.execute();
                }else{
                    // 如果用户中断：
                    executor.cancel();
                }
            }
        };


        AndPermission.with(this)
                .permission(
                        Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO,Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.VIBRATE, Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.WAKE_LOCK, Manifest.permission.MODIFY_AUDIO_SETTINGS,
                        Manifest.permission.READ_PHONE_STATE,Manifest.permission.RECEIVE_BOOT_COMPLETED
                )
                .rationale(mRationale)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                            // 这些权限被用户总是拒绝。
                        }
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                        // 这些权限被用户总是拒绝。
                        }
                    }
                })
                .start();
    }

    /**
     * init views
     */
    private void initView() {
        unreadLabel = (TextView) findViewById(R.id.unread_msg_number);
        unreadAddressLable = (TextView) findViewById(R.id.unread_address_number);
        mTabs = new Button[3];
        mTabs[0] = (Button) findViewById(R.id.btn_conversation);
        mTabs[1] = (Button) findViewById(R.id.btn_address_list);
        mTabs[2] = (Button) findViewById(R.id.btn_setting);
        // select first tab
        mTabs[0].setSelected(true);
    }

}

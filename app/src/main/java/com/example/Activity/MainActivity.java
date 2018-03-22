package com.example.Activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.Toast;


import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.example.Contacts.ContactsFragment;
import com.example.Contacts.MyBroadcast;
import com.example.Contacts.MyContactListener;
import com.example.Conversation.ConversationFragment;
import com.example.R;
import com.example.UserCenter.UserCenterFragment;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.exceptions.HyphenateException;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener, ViewPager.OnPageChangeListener{

    protected MainActivity activity;

    protected static final String TAG = "MainActivity";


    private ViewPager viewPager;
    private BottomNavigationBar bottomNavigationBar;
    private List<Fragment> mList; //ViewPager的数据源
    private ShapeBadgeItem mShapeBadgeItem;//添加角标
    private TextBadgeItem mTextBadgeItem;
    private Context context;
    private boolean ckeck;
//    private List<String> Myuser;
    private IntentFilter intentFilter;
    private MyBroadcast FriendsChangeReceiver;
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
                    EMClient.getInstance().contactManager().setContactListener(new MyContactListener(MainActivity.this));
                   // EMChat.getInstance().setAppInited();


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


        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MyFriends= EMClient.getInstance().contactManager().getAllContactsFromServer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                getContact();
            }
        }).start();*/

        setContentView(R.layout.activity_main);
        // runtime permission for android 6.0, just require all permissions here for simple
        requestPermissions();

        initBottomNavigationBar();
        initViewPager();

    }

    //初始化ViewPager
    private void initViewPager() {
        mList = new ArrayList<>();
        mList.add(new ConversationFragment());
        mList.add(new ContactsFragment());
        mList.add(new UserCenterFragment());

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOnPageChangeListener(this);
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager(), mList);
        viewPager.setAdapter(mainAdapter); //视图加载适配器
    }

    /**
     * 初始化，初始化底部导航
     */
    private void initBottomNavigationBar() {

        /**
         * bottomNavigation 设置
         */

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        /** 导航基础设置 包括按钮选中效果 导航栏背景色等 */
        bottomNavigationBar
                .setTabSelectedListener(this)
                .setMode(BottomNavigationBar.MODE_SHIFTING)
                /**
                 *  setMode() 内的参数有三种模式类型：
                 *  MODE_DEFAULT 自动模式：导航栏Item的个数<=3 用 MODE_FIXED 模式，否则用 MODE_SHIFTING 模式
                 *  MODE_FIXED 固定模式：未选中的Item显示文字，无切换动画效果。
                 *  MODE_SHIFTING 切换模式：未选中的Item不显示文字，选中的显示文字，有切换动画效果。
                 */

                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)
                /**
                 *  setBackgroundStyle() 内的参数有三种样式
                 *  BACKGROUND_STYLE_DEFAULT: 默认样式 如果设置的Mode为MODE_FIXED，将使用BACKGROUND_STYLE_STATIC
                 *                                    如果Mode为MODE_SHIFTING将使用BACKGROUND_STYLE_RIPPLE。
                 *  BACKGROUND_STYLE_STATIC: 静态样式 点击无波纹效果
                 *  BACKGROUND_STYLE_RIPPLE: 波纹样式 点击有波纹效果
                 */

                .setActiveColor("#FF107FFD") //选中颜色
                .setInActiveColor("#e9e6e6") //未选中颜色
                .setBarBackgroundColor("#1ccbae");//导航栏背景色

        mTextBadgeItem = new TextBadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.colorPrimary)
                .setText("5")
                .setTextColorResource(R.color.btn_white_pressed)
                .setBorderColorResource(R.color.colorPrimaryDark)  //外边界颜色
                .setHideOnSelect(false);

        mShapeBadgeItem = new ShapeBadgeItem()
                .setShape(ShapeBadgeItem.SHAPE_OVAL)
                .setShapeColor(R.color.colorPrimary)
                .setShapeColorResource(R.color.colorPrimary)
                .setSizeInDp(this,10,10)
                .setEdgeMarginInDp(this,2)
//                .setSizeInPixels(30,30)
//                .setEdgeMarginInPixels(-1)
                .setGravity(Gravity.TOP | Gravity.END)
                .setHideOnSelect(false);

        /** 添加导航按钮 */
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.message_icon_normal, "消息").setActiveColorResource(R.color.btn_white_normal).setInactiveIconResource(R.drawable.message_icon_selected).setInActiveColorResource(R.color.main_bottom_button_bg).setBadgeItem(mTextBadgeItem))
                .addItem(new BottomNavigationItem(R.drawable.contacts_icon_normal, "联系人").setActiveColorResource(R.color.btn_white_normal).setInactiveIconResource(R.drawable.contacts_icon_selected).setInActiveColorResource(R.color.main_bottom_button_bg).setBadgeItem(mShapeBadgeItem))
                .addItem(new BottomNavigationItem(R.drawable.usercenter_icon_normal, "个人信息").setActiveColorResource(R.color.btn_white_normal).setInactiveIconResource(R.drawable.usercenter_icon_selected).setInActiveColorResource(R.color.main_bottom_button_bg))
                .setFirstSelectedPosition(0 )
                .initialise(); //initialise 一定要放在 所有设置的最后一项
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

    @Override
    public void onTabSelected(int position) {
        //tab被选中
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //ViewPager滑动
        bottomNavigationBar.selectTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onDestroy();
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /*public void getContact(){
                try {
                    Myuser= EMClient.getInstance().contactManager().getAllContactsFromServer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }*/


}

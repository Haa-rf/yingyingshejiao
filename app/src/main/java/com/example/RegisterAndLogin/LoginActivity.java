package com.example.RegisterAndLogin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Activity.MainActivity;
import com.example.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;

/**
 * A login screen that offers login via account/password.
 */
public class LoginActivity extends AppCompatActivity{

    private static final String TAG = "LoginActivity";
    public static final int REQUEST_CODE_SETNICK = 1;
    private EditText usernameEditText;
    private EditText passwordEditText;

    private boolean progressShow;
    private boolean autoLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // enter the main activity if already logged in
        //注释部分是demo的源码实现
//        if (DemoHelper.getInstance().isLoggedIn()) {
//            autoLogin = true;
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//
//            return;
//        }
        setContentView(R.layout.activity_login);

        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);

        // if user changed, clear the password
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordEditText.setText(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN ))) {
                    login(null);
                    return true;
                }
                else{
                    return false;
                }
            }
        });

//        if (DemoHelper.getInstance().getCurrentUsernName() != null) {
//            usernameEditText.setText(DemoHelper.getInstance().getCurrentUsernName());
//        }

//        TextView serviceCheckTV = (TextView) findViewById(R.id.txt_service_ckeck);
//        serviceCheckTV.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    /**
     * login
     *
     * @param view
     */
    public void login(View view) {
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            Toast.makeText(this, "network is not available", Toast.LENGTH_SHORT).show();
            return;
        }
        String currentUsername = usernameEditText.getText().toString().trim();
        String currentPassword = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(currentUsername)) {
            Toast.makeText(this,"Username cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        progressShow = true;
        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                Log.d(TAG, "EMClient.getInstance().onCancel");
                progressShow = false;
            }
        });
        pd.setMessage("is login...");
        pd.show();

        // After logout，the DB may still be accessed due to async callback, so the DB will be re-opened again.
        // close it before login to make sure DemoDB not overlap
        //数据库操作
        //DemoDBManager.getInstance().closeDB();

        // reset current user name before login
        //DemoHelper.getInstance().setCurrentUserName(currentUsername);

        final long start = System.currentTimeMillis();
        // call login method
        Log.d(TAG, "EMClient.getInstance().login");
        EMClient.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "login: onSuccess");

                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                // update current user's display name for APNs
//                boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
//                        DemoApplication.currentUserNick.trim());
//                if (!updatenick) {
//                    Log.e("LoginActivity", "update current user nick fail");
//                }
//
//                if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
//                    pd.dismiss();
//                }
//                // get user's info (this should be get from App's server or 3rd party service)
//                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();

                Intent intent = new Intent(LoginActivity.this,
                        MainActivity.class);
                startActivity(intent);

                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d(TAG, "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                Log.d(TAG, "login: onError: " + code);
                if (!progressShow) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    /**
     * register
     *
     * @param view
     */
    public void register(View view) {
        startActivityForResult(new Intent(this, RegisterActivity.class), 0);
    }

//    /**
//     * SDK service check
//     *
//     * @param v
//     */
//    public void serviceCheck(View v) {
//        startActivity(new Intent(this, ServiceCheckActivity.class));
//    }

    @Override
    protected void onResume() {
        super.onResume();
        if (autoLogin) {
            return;
        }
    }
}


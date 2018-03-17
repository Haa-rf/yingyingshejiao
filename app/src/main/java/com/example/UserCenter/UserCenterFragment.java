package com.example.UserCenter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.R;

/**
 *
 */
public class UserCenterFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout userInfo_layout;
    private RelativeLayout setting_layout;
    private RelativeLayout Title_layout;
    private TextView Title_text;
    private Intent intent;

    public UserCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_center,container,false);
        userInfo_layout = (RelativeLayout)view.findViewById(R.id.userInfo);
        setting_layout = (RelativeLayout)view.findViewById(R.id.setting);
        Title_layout = (RelativeLayout)view.findViewById(R.id.userTitle);
        Title_text = (TextView)view.findViewById(R.id.userTitleText);
        /**
         * 还需要从数据库获取用户的信息并显示在界面上
         */
        Title_text.setText("个人信息");

        //添加按钮点击事件
        userInfo_layout.setOnClickListener(this);
        setting_layout.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }


//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//
//        super.onActivityCreated(savedInstanceState);
//        if(savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
//            return;
//
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.userInfo:
                intent = new Intent(getActivity(), UserInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.setting:
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
        }
    }

}

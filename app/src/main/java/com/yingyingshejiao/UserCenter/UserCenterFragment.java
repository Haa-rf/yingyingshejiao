package com.yingyingshejiao.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yingyingshejiao.DBM.DBMUtil;
import com.yingyingshejiao.JavaBean.User;
import com.yingyingshejiao.R;
import com.hyphenate.chat.EMClient;

/**
 *
 */
public class UserCenterFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout userInfo_layout;
    private RelativeLayout setting_layout;
    private RelativeLayout Title_layout;
    private TextView Title_text;
    private Intent intent;
    private ImageView head;
    private TextView nickname;
    private TextView username;
    private TextView sex;
    private TextView sign;
    private User Me;
    private String user_name;
    private String nick_name;
    private String user_image;
    private String user_sex;
    private String user_sign;


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
        head = (ImageView)view.findViewById(R.id.imageButton);
        nickname = (TextView)view.findViewById(R.id.nickname);
        username = (TextView)view.findViewById(R.id.userID);
        sex =(TextView)view.findViewById(R.id.sex);
        sign=(TextView)view.findViewById(R.id.sign);
        /**
         * 还需要从数据库获取用户的信息并显示在界面上
         */

        user_name = EMClient.getInstance().getCurrentUser();
        username.setText("UserName:  "+user_name);
        Me = DBMUtil.query(getContext(),user_name);
        if (Me == null){
            Me = new User();
            Me.setUser_name(user_name);
            Me.setUser_nickname("嘤嘤");
            Me.setUser_image(Integer.toString(R.drawable.head_image_m1));
            head.setImageResource(R.drawable.head_image_m1);
            DBMUtil.addUser(getContext(),Me);
            nickname.setText("NickName:  "+"嘤嘤");
        }else {
            nick_name = Me.getUser_nickname();
            user_image = Me.getUser_image();
            user_sex = Me.getUser_sex();
            user_sign = Me.getUser_sign();
            head.setImageResource(Integer.parseInt(user_image));
            nickname.setText("NickName:  "+nick_name);
            sex.setText("Sex            :  "+user_sex);
            sign.setText(user_sign);
        }
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInfo_layout = (RelativeLayout)view.findViewById(R.id.userInfo);
        setting_layout = (RelativeLayout)view.findViewById(R.id.setting);
        Title_layout = (RelativeLayout)view.findViewById(R.id.userTitle);
        Title_text = (TextView)view.findViewById(R.id.userTitleText);
        head = (ImageView)view.findViewById(R.id.imageButton);
        nickname = (TextView)view.findViewById(R.id.nickname);
        username = (TextView)view.findViewById(R.id.userID);
        sex =(TextView)view.findViewById(R.id.sex);
        sign=(TextView)view.findViewById(R.id.sign);
        /**
         * 还需要从数据库获取用户的信息并显示在界面上
         */

        user_name = EMClient.getInstance().getCurrentUser();
        username.setText("UserName:  "+user_name);
        Me = DBMUtil.query(getContext(),user_name);
        if (Me == null){
            Me = new User();
            Me.setUser_name(user_name);
            Me.setUser_nickname("嘤嘤");
            Me.setUser_image(Integer.toString(R.drawable.head_image_m1));
            head.setImageResource(R.drawable.head_image_m1);
            DBMUtil.addUser(getContext(),Me);
            nickname.setText("NickName:  "+"嘤嘤");
        }else {
            nick_name = Me.getUser_nickname();
            user_image = Me.getUser_image();
            user_sex = Me.getUser_sex();
            user_sign = Me.getUser_sign();
            head.setImageResource(Integer.parseInt(user_image));
            nickname.setText("NickName:  "+nick_name);
            sex.setText("Sex            :  "+user_sex);
            sign.setText(user_sign);
        }
        Title_text.setText("个人信息");
    }
}

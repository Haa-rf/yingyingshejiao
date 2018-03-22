package com.example.Contacts;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.WINDOW_SERVICE;


public class GroupFragment extends Fragment {
    private List<Group> groupList = new ArrayList<Group>();
    List<EMGroup> EMgrouplist;
    Button creatGroup;
    private GroupListAdapter groupListAdapter;
    ListView groupListView;
    public GroupFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_group, container, false);
        groupListView=view.findViewById(R.id.groupList);
        //从环信服务器获得加入的群
        try {
            EMgrouplist = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        //如果加入的群不为空，就把群名传递给groupList
        if(EMgrouplist!=null&&EMgrouplist.size()!=0) {
            for (int i = 0; i < EMgrouplist.size(); i++) {
                groupList.add(new Group(EMgrouplist.get(i).getGroupName(),EMgrouplist.get(i).getGroupId(), R.drawable.testpic));
            }
        }
        //把listView和groupList适配
        groupListAdapter=new GroupListAdapter(getActivity(), R.layout.contact, groupList);
        groupListView.setAdapter(groupListAdapter);

        //点击按钮创建群
        creatGroup=view.findViewById(R.id.creatGroup);
        creatGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatGroup();

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    public void CreatGroup(){
        final WindowManager windowManager=(WindowManager)getActivity().getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams params=new WindowManager.LayoutParams();
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        params.width=WindowManager.LayoutParams.MATCH_PARENT;
        params.height=WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity= Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL;
        params.alpha=1f;
        final LinearLayout CreateGroup= (LinearLayout) inflater.inflate(R.layout.creat_group,null);
        Button Create=CreateGroup.findViewById(R.id.Create);
        TextView Cancel=CreateGroup.findViewById(R.id.cancel);
        final EditText Groupname=CreateGroup.findViewById(R.id.editGroupName);
        final String[] Mymember=new String[1];
        Mymember[0] = "user2";
        final EditText Groupdesc=CreateGroup.findViewById(R.id.editGroupDesc);
        final EditText Reason=CreateGroup.findViewById(R.id.reason);
        windowManager.addView(CreateGroup,params);
        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EMGroupOptions option = new EMGroupOptions();
                        option.maxUsers = 200;
                        option.style = EMGroupManager.EMGroupStyle.EMGroupStylePrivateMemberCanInvite;

                        try {
                            EMClient.getInstance().groupManager().createGroup(Groupname.getText().toString(), Groupdesc.getText().toString(),Mymember , Reason.getText().toString(), option);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(),"创建成功",Toast.LENGTH_SHORT).show();

                                }
                            });
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "创建失败", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }


                    }
                }).start();

                windowManager.removeView(CreateGroup);
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowManager.removeView(CreateGroup);
            }
        });


    }


}

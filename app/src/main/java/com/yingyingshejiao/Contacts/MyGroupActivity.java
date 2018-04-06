package com.yingyingshejiao.Contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yingyingshejiao.Chat.ChatActivity;
import com.yingyingshejiao.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

public class MyGroupActivity extends AppCompatActivity {
    private List<Group> groupList = new ArrayList<Group>();
    List<EMGroup> EMgrouplist;
    private Button creatGroup;
    private String GroupName;
    private String GroupId;
    private GroupListAdapter groupListAdapter;
    ListView groupListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_group);
        creatGroup=(Button) findViewById(R.id.create_Group);
        groupListView=(ListView) findViewById(R.id.groupList);
        //从环信服务器获得加入的群并加载到列表
        getGroupList();
        EMgrouplist= EMClient.getInstance().groupManager().getAllGroups();
        if(EMgrouplist!=null&&EMgrouplist.size()!=0) {
            for (int i = 0; i < EMgrouplist.size(); i++) {
                GroupName=EMgrouplist.get(i).getGroupName();
                GroupId=EMgrouplist.get(i).getGroupId();
                groupList.add(new Group(GroupName,GroupId, R.drawable.testpic));

            }

        }


        creatGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatGroup();

            }
        });
        groupListAdapter=new GroupListAdapter(MyGroupActivity.this, R.layout.contact, groupList);
        groupListView.setAdapter(groupListAdapter);
        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Group clickedGroup=groupList.get(position);
                Intent intent = new Intent(MyGroupActivity.this, ChatActivity.class);
                // it is group chat
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,EaseConstant.CHATTYPE_GROUP);
                intent.putExtra(EaseConstant.EXTRA_USER_ID,clickedGroup.getGroupId());
                startActivity(intent);
                finish();
            }
        });

    }



    public void getGroupList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().groupManager().getJoinedGroupsFromServer();

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void CreatGroup(){
        final WindowManager windowManager=(WindowManager)getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams params=new WindowManager.LayoutParams();
        LayoutInflater inflater=LayoutInflater.from(MyGroupActivity.this);
        params.width=WindowManager.LayoutParams.MATCH_PARENT;
        params.height=WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity= Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL;
        params.alpha=1f;
        final LinearLayout CreateGroup= (LinearLayout) inflater.inflate(R.layout.creat_group,null);
        Button Create=CreateGroup.findViewById(R.id.Create);
        TextView Cancel=CreateGroup.findViewById(R.id.cancel);
        final EditText Groupname=CreateGroup.findViewById(R.id.editGroupName);
        final String[] Mymember=new String[1];
        Mymember[0] = "user02";
        final EditText Groupdesc=CreateGroup.findViewById(R.id.editGroupDesc);
        final EditText Reason=CreateGroup.findViewById(R.id.reason);
        windowManager.addView(CreateGroup,params);
        //创建群
        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EMGroupOptions option = new EMGroupOptions();
                        option.maxUsers = 200;
                        option.style = EMGroupManager.EMGroupStyle.EMGroupStylePublicOpenJoin;

                        try {
                            EMClient.getInstance().groupManager().createGroup(Groupname.getText().toString(), Groupdesc.getText().toString(),Mymember , Reason.getText().toString(), option);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MyGroupActivity.this,"创建成功",Toast.LENGTH_SHORT).show();

                                }
                            });
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MyGroupActivity.this, "创建失败", Toast.LENGTH_SHORT).show();

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

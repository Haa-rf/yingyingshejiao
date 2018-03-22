package com.example.Contacts;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Activity.MainActivity;
import com.example.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.widget.EaseImageView;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class GroupContactFragment extends Fragment {
    private static final String GROUP_TEXT = "group_text";//分组Map的key
    private static final String CONTACT_TEXT="contact_text";//联系人备注的Map的key
    private List<Map<String,String>> groupData = new ArrayList<Map<String, String>>();
    private List<String> MyFriends;
    private List<ArrayList<Map<String,String>>> ContactData = new ArrayList<ArrayList<Map<String, String>>>();
    private ExpandableListView ExList;
    private ExAdapter adapter;



    public GroupContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_group_contact, container, false);
        // Inflate the layout for this fragment
        //设置各种适配器
        new Thread(new Runnable() {
            @Override
            public void run() {
                getContact();
            }
        });

        if(MyFriends!=null&&MyFriends.size()!=0){
            List<Map<String, String>> Contact = new ArrayList<Map<String, String>>();
            for(int i=0;i<MyFriends.size();i++){
                Map<String,String> curContactData=new HashMap<String, String>();
                curContactData.put(CONTACT_TEXT,MyFriends.get(i).toString());
                Contact.add(curContactData);
            }
            Map<String,String> Self=new HashMap<String, String>();
            Self.put(CONTACT_TEXT,"我");
            Contact.add(Self);
            ContactData.add((ArrayList<Map<String, String>>)Contact);
        }



       /* for(int i=0;i<5;i++){
            Map<String, String> curGroupMap = new HashMap<String, String>();
            curGroupMap.put(GROUP_TEXT,"第"+i+"组");
            List<Map<String, String>> Contact = new ArrayList<Map<String, String>>();
            for (int j=0;j<5;j++){
                Map<String, String> curContactMap = new HashMap<String, String>();
                curContactMap.put(CONTACT_TEXT,"联系人"+j);
                Contact.add(curContactMap);
            }
            ContactData.add((ArrayList<Map<String, String>>)Contact);
            groupData.add(curGroupMap);
        }*/
        Map<String, String> curGroupMap = new HashMap<String, String>();
        curGroupMap.put(GROUP_TEXT,"所有人");
        groupData.add(curGroupMap);


        adapter = new ExAdapter(getActivity());
        ExList=view.findViewById(R.id.GroupContact);
        ExList.setAdapter(adapter);
        ExList.setGroupIndicator(null);
        ExList.setDivider(null);
        return view;
    }

    class ExAdapter extends BaseExpandableListAdapter {
        Context context;

        public ExAdapter(Context context) {
            super();
            this.context = getActivity();
        }
        //得到大组成员的view
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            View GroupView = convertView;
            if (GroupView == null) {
                GroupView= getActivity().getLayoutInflater().from(context).inflate(
                        R.layout.group_contact, null);      //使用inflate获取分组的布局
            }

            TextView tittle = GroupView.findViewById(R.id.GroupTittle);
            tittle.setText(getGroup(groupPosition).toString());

            EaseImageView image=GroupView.findViewById(R.id.icon_exp);

            if (isExpanded)//大组展开时
                image.setBackgroundResource(R.drawable.tubiao2);
            else//大组合并时
                image.setBackgroundResource(R.drawable.tubiao1);

            return GroupView;
        }
        //得到大组成员的id
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }
        //得到大组成员
        public Object getGroup(int groupPosition) {
            return groupData.get(groupPosition).get(GROUP_TEXT).toString();
        }
        //得到大组成员总数
        public int getGroupCount() {
            return groupData.size();

        }

        // 得到小组成员的view
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            View ContactView = convertView;
            if (ContactView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                ContactView = inflater.inflate(R.layout.contact, null);
            }
            TextView UserName = ContactView.findViewById(R.id.username);
            UserName.setText(ContactData.get(groupPosition).get(childPosition).get(CONTACT_TEXT).toString());
            return ContactView;
        }
        //得到小组成员id
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }
        //得到小组成员的名称
        public Object getChild(int groupPosition, int childPosition) {
            return ContactData.get(groupPosition).get(childPosition).get(CONTACT_TEXT).toString();
        }
        //得到小组成员的数量
        public int getChildrenCount(int groupPosition) {
            return ContactData.get(groupPosition).size();
        }
        /**
         * Indicates whether the child and group IDs are stable across changes to the
         * underlying data.
         * 表明大組和小组id是否稳定的更改底层数据。
         * @return whether or not the same ID always refers to the same object
         */
        public boolean hasStableIds() {
            return true;
        }
        //得到小组成员是否被选择
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

    public void getContact(){
        try {
            MyFriends= EMClient.getInstance().contactManager().getAllContactsFromServer();
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

}

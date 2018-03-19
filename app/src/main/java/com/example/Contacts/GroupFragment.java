package com.example.Contacts;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.R;

import java.util.ArrayList;
import java.util.List;


public class GroupFragment extends Fragment {
    private List<Group> groupList = new ArrayList<Group>();
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
        for(int i=1;i<5;i++) {
            groupList.add(new Group("群"+i,R.drawable.testpic));
        }
        groupListAdapter=new GroupListAdapter(getActivity(), R.layout.contact, groupList);
        groupListView.setAdapter(groupListAdapter);
        // Inflate the layout for this fragment
        return view;
    }


}

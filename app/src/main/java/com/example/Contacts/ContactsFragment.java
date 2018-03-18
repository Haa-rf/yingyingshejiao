package com.example.Contacts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.R;

public class ContactsFragment extends Fragment {
    private Button contactButton;
    private Button groupButton;
    private Button addButton;
    private Fragment fragment;
    private Fragment currentFragment;
    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_contact, container, false);
        FragmentManager manager1 = getActivity().getSupportFragmentManager();
        addFragment("Contact");
        //点击对应按钮更换对应fragment
        contactButton=view.findViewById(R.id.Contact);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment("Contact");
            }
        });

        groupButton=view.findViewById(R.id.Group);
        groupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment("Group");
            }
        });

        addButton=view.findViewById(R.id.AddButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment("Search");

            }
        });
        return view;
    }


    private void addFragment(String fTag) {

        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //判断这个标签是否存在Fragment对象,如果存在则返回，不存在返回null
        fragment =  manager.findFragmentByTag(fTag);
        // 如果这个fragment不存于栈中
        if (fragment == null) {
            //初始化Fragment事物

            //根据RaioButton点击的Button传入的tag，实例化，添加显示不同的Fragment
            if (fTag.equals("Contact")) {
                fragment = new GroupContactFragment();
            } else if (fTag.equals("Group")) {
                fragment = new GroupFragment();
            } else if (fTag.equals("Search")) {
                fragment = new SearchFragment();
            }
            //在添加之前先将上一个Fragment隐藏掉
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.MyFragment, fragment, fTag);
            transaction.commit();
            //更新可见
            currentFragment = fragment;
        } else {
            //如果添加的Fragment已经存在，则将隐藏掉的Fragment再次显示,其余当前
            transaction = manager.beginTransaction();
            transaction.show(fragment);
            transaction.hide(currentFragment);
            //更新可见
            currentFragment = fragment;
            transaction.commit();


        }


    }

}

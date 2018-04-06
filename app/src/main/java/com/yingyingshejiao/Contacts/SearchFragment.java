package com.yingyingshejiao.Contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yingyingshejiao.R;



public class SearchFragment extends Fragment {

    private Button findContact;
    private Button findGroup;
    private EditText searchText;


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search, container, false);
        findContact=view.findViewById(R.id.SearchContact);
        findGroup=view.findViewById(R.id.SearchGroup);
        searchText=view.findViewById(R.id.SearchView);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                findContact.setText(String.format("加人：%s", searchText.getText()));
                findGroup.setText(String.format("加群：%s", searchText.getText()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        findContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchText.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"请输入ID",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(),"找到人"+searchText.getText().toString(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), AddContactActivity.class);
                    getActivity().startActivity(intent);
                }
            }
        });
        // Inflate the layout for this fragment
        findGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchText.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"请输入ID",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent joinGroup = new Intent(getActivity(), JoinGroupActivity.class);
                    Bundle Group=new Bundle();
                    Group.putString("GroupId",searchText.getText().toString());
                    joinGroup.putExtras(Group);
                    startActivity(joinGroup);
                }
            }
        });
        return view;
    }


}

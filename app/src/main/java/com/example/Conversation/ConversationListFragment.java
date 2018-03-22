package com.example.Conversation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.Activity.MainActivity;
import com.example.Chat.ChatActivity;
import com.example.Constant;
import com.example.R;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMConversationListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseBaseFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.widget.EaseConversationList;

/**
 * Created by apple on 20/03/2018.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ConversationListFragment extends EaseConversationListFragment {
    protected void initView(){
        super.initView();

    }
    protected void setUpView() {
        super.setUpView();
        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                String username=conversation.conversationId();
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                if(conversation.getType()== EMConversation.EMConversationType.GroupChat){
                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,EaseConstant.CHATTYPE_GROUP);
                }
                intent.putExtra(EaseConstant.EXTRA_USER_ID,username);
                startActivity(intent);

            }
        });
    }
}

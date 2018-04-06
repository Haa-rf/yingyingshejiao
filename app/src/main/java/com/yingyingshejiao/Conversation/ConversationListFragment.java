package com.yingyingshejiao.Conversation;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.yingyingshejiao.Chat.ChatActivity;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

/**
 * Created by apple on 20/03/2018.
 */

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

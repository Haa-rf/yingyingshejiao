package com.yingyingshejiao.JavaBean;

import java.util.Date;

/**
 * Created by ScoutB on 2018/3/18.
 */

public class ChatMessage {
    private String friend_name;
    private String chat_msg_content;
    private String chat_msg_time;

    public ChatMessage(){
        this.chat_msg_time=CurrentTime();
    }

    public ChatMessage(String friend_name, String chat_msg_content){
        this.friend_name=friend_name;
        this.chat_msg_content=chat_msg_content;
        this.chat_msg_time=CurrentTime();
    }

    private static String CurrentTime(){
        Date date = new Date();
        String rt = date.toString();
        return rt;
    }

    public String getFriend_name(){
        return friend_name;
    }

    public String getChat_msg_content(){
        return chat_msg_content;
    }

    public void setFriend_name(){
        this.friend_name=friend_name;
    }

    public void setChat_msg_content(){
        this.chat_msg_content=chat_msg_content;
    }
}

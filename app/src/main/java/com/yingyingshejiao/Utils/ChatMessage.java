package com.yingyingshejiao.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import static com.hyphenate.easeui.EaseConstant.CHATTYPE_GROUP;

/**
 * message类 ，用于封装有关message的相关操作
 * Created by 苟仁福 on 2018/3/14.
 */

public class ChatMessage {

    /**
     * 发送文本消息
     * @param content 发送的内容
     * @param toChatUsername 对方用户或者群聊的id
     * @param chatType 聊天类型默认为单聊，com.hyphenate.easeui.EaseConstant中存放，单聊为1，群聊为2，聊天室为3
     * @param context 上下文信息
     */
    public void sendTxt(String content, String toChatUsername, int chatType, final Context context){
        EMMessage message = EMMessage.createTxtSendMessage(content,toChatUsername);
        //如果是群聊，设置chattype，默认是单聊
        if (chatType == CHATTYPE_GROUP)
            message.setChatType(EMMessage.ChatType.GroupChat);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
        //设置发送消息的回调
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.d("SendMessage","发送信息成功");
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(context,"消息发送失败",Toast.LENGTH_SHORT).show();
                Log.d("SendMessage","发送信息失败");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    /**
     * 发送语音消息
     * @param filePath 语音文件路径
     * @param length 录音时长
     * @param toChatUsername 对方用户或者群聊的id
     * @param chatType 聊天类型默认为单聊，com.hyphenate.easeui.EaseConstant中存放，单聊为1，群聊为2，聊天室为3
     * @param context 上下文信息
     */
    public void sendVoice(String filePath,int length, String toChatUsername, int chatType, final Context context){
        //filePath为语音文件路径，length为录音时间(秒)
        EMMessage message = EMMessage.createVoiceSendMessage(filePath,length, toChatUsername);
        //如果是群聊，设置chattype，默认是单聊
        if (chatType == CHATTYPE_GROUP)
            message.setChatType(EMMessage.ChatType.GroupChat);
        EMClient.getInstance().chatManager().sendMessage(message);
        //设置发送消息的回调
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.d("SendMessage","发送语音成功");
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(context,"语音发送失败",Toast.LENGTH_SHORT).show();
                Log.d("SendMessage","发送语音失败");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    /**
     * 发送图片信息
     * @param imagePath 本地图片路径
     * @param original 是否发送原图，false为不发送原图
     * @param toChatUsername 对方用户或者群聊的id
     * @param chatType 聊天类型默认为单聊，com.hyphenate.easeui.EaseConstant中存放，单聊为1，群聊为2，聊天室为3
     * @param context 上下文信息
     */
    public void sendImage(String imagePath,boolean original, String toChatUsername, int chatType, final Context context){
        //imagePath为图片本地路径，false为不发送原图（默认超过100k的图片会压缩后发给对方），需要发送原图传true
        EMMessage message = EMMessage.createImageSendMessage(imagePath,original,toChatUsername);
        //如果是群聊，设置chattype，默认是单聊
        if (chatType == CHATTYPE_GROUP)
            message.setChatType(EMMessage.ChatType.GroupChat);
        EMClient.getInstance().chatManager().sendMessage(message);
        //设置发送消息的回调
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.d("SendMessage","发送图片成功");
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(context,"图片发送失败",Toast.LENGTH_SHORT).show();
                Log.d("SendMessage","发送图片失败");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    /**
     * 发送视频信息
     * @param videoPath 视频本地路径
     * @param thumbPath 视频预览图路径
     * @param videoLength 视频时间长度
     * @param toChatUsername 对方用户或者群聊的id
     * @param chatType 聊天类型默认为单聊，com.hyphenate.easeui.EaseConstant中存放，单聊为1，群聊为2，聊天室为3
     * @param context 上下文信息
     */
    public void sendVideo(String videoPath,String thumbPath,int videoLength, String toChatUsername, int chatType, final Context context){
        //videoPath为视频本地路径，thumbPath为视频预览图路径，videoLength为视频时间长度
        EMMessage message = EMMessage.createVideoSendMessage(videoPath, thumbPath, videoLength, toChatUsername);
        //如果是群聊，设置chattype，默认是单聊
        if (chatType == CHATTYPE_GROUP)
            message.setChatType(EMMessage.ChatType.GroupChat);
        EMClient.getInstance().chatManager().sendMessage(message);
        //设置发送消息的回调
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.d("SendMessage","发送视频成功");
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(context,"视频发送失败",Toast.LENGTH_SHORT).show();
                Log.d("SendMessage","发送视频失败");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    /**
     *
     * @param latitude 纬度
     * @param longitude 经度
     * @param locationAddress 具体位置内容
     * @param toChatUsername 对方用户或者群聊的id
     * @param chatType 聊天类型默认为单聊，com.hyphenate.easeui.EaseConstant中存放，单聊为1，群聊为2，聊天室为3
     * @param context 上下文信息
     */
    public void sendLocation(Double latitude,Double longitude,String locationAddress, String toChatUsername, int chatType, final Context context){
        //latitude为纬度，longitude为经度，locationAddress为具体位置内容
        EMMessage message = EMMessage.createLocationSendMessage(latitude, longitude, locationAddress, toChatUsername);
        //如果是群聊，设置chattype，默认是单聊
        if (chatType == CHATTYPE_GROUP)
            message.setChatType(EMMessage.ChatType.GroupChat);
        EMClient.getInstance().chatManager().sendMessage(message);
        //设置发送消息的回调
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.d("SendMessage","发送位置成功");
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(context,"位置发送失败",Toast.LENGTH_SHORT).show();
                Log.d("SendMessage","发送位置失败");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    /**
     *
     * @param filePath 文件路径
     * @param toChatUsername 对方用户或者群聊的id
     * @param chatType 聊天类型默认为单聊，com.hyphenate.easeui.EaseConstant中存放，单聊为1，群聊为2，聊天室为3
     * @param context 上下文信息
     */
    public void sendFile(String filePath, String toChatUsername, int chatType, final Context context){
        EMMessage message = EMMessage.createFileSendMessage(filePath, toChatUsername);
        //如果是群聊，设置chattype，默认是单聊
        if (chatType == CHATTYPE_GROUP)
            message.setChatType(EMMessage.ChatType.GroupChat);
        EMClient.getInstance().chatManager().sendMessage(message);
        //设置发送消息的回调
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.d("SendMessage","发送文件成功");
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(context,"文件发送失败",Toast.LENGTH_SHORT).show();
                Log.d("SendMessage","发送文件失败");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}

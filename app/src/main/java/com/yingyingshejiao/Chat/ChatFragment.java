package com.yingyingshejiao.Chat;

/**
 * Created by apple on 18/03/2018.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yingyingshejiao.Call.CallManager;
import com.yingyingshejiao.Call.VideoCallActivity;
import com.yingyingshejiao.Call.VoiceCallActivity;
import com.yingyingshejiao.R;
import com.yingyingshejiao.UserCenter.UserInfoActivity;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.util.PathUtil;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by apple on 18/03/2018.
 */

public class ChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentHelper {

    private static final int ITEM_VIDEO = 11;
    private static final int ITEM_FILE = 12;
    private static final int ITEM_VOICE_CALL = 13;
    private static final int ITEM_VIDEO_CALL = 14;

    private static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final int REQUEST_CODE_SELECT_FILE = 12;
    private static final int REQUEST_CODE_GROUP_DETAIL = 13;
    private static final int REQUEST_CODE_CONTEXT_MENU = 14;

    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        return super.onCreateView(inflater,container,saveInstanceState);

    }

    public void setUpView(){
        setChatFragmentHelper(this);
        super.setUpView();
        //inputMenu.getEmojiconMenu().addEmojiconGroup(EmojiiconExampleGroupData.getData());
    }
    protected void registerExtendMenuItem(){
        super.registerExtendMenuItem();
        inputMenu.registerExtendMenuItem(R.string.attach_voice_call,R.drawable.ease_chat_voice_call_self,ITEM_VOICE_CALL,extendMenuItemClickListener);
        inputMenu.registerExtendMenuItem(R.string.attach_file,R.drawable.ease_chat_item_file,ITEM_FILE,extendMenuItemClickListener);
        inputMenu.registerExtendMenuItem(R.string.attach_video,R.drawable.em_chat_video_call_normal,ITEM_VIDEO,extendMenuItemClickListener);
    }
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode) {
                case REQUEST_CODE_SELECT_VIDEO: //发送选中的视频
                    if (data != null) {
                        int duration = data.getIntExtra("dur", 0);
                        String videoPath = data.getStringExtra("path");
                        File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                            ThumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.close();
                            sendVideoMessage(videoPath, file.getAbsolutePath(), duration);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_FILE: //发送选中的文件
                    if (data != null) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            sendFileByUri(uri);
                        }
                    }
                    break;
                default:
                    break;
            }
        }


    }
    @Override
    public void onSetMessageAttributes(EMMessage message) {
        message.setAttribute("headPic","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=359604599,305372113&fm=27&gp=0.jpg");
        message.setAttribute("nickname","Nick");

    }

    @Override
    public void onEnterToChatDetails() {


    }

    @Override
    public void onAvatarClick(String username) {
        Intent intent = new Intent(getActivity(),UserInfoActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);

    }

    @Override
    public void onAvatarLongClick(String username) {
        inputAtUsername(username);

    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {

    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        switch (itemId) {
            case ITEM_VIDEO:
                startVideoCall();
                break;
            case ITEM_FILE: //file
                selectFileFromLocal();
                break;
            case ITEM_VOICE_CALL:
                 startVoiceCall();
                break;
            case ITEM_VIDEO_CALL:
                break;
            default:
                break;
        }
        return false;
    }
    protected void selectFileFromLocal() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) { //api 19 and later, we can't use this way, demo just select from images
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return null;
    }
    public void startVoiceCall(){
        // 语音通话
        Intent intent = new Intent(ChatFragment.this.getActivity(), VoiceCallActivity.class);
        // 设置被呼叫方 username
        toChatUsername = fragmentArgs.getString(EaseConstant.EXTRA_USER_ID);
        CallManager.getInstance().setChatId(toChatUsername);
        // 设置通话为自己呼出的
        CallManager.getInstance().setInComingCall(false);
        // 设置当前通话类型为音频通话
        CallManager.getInstance().setCallType(CallManager.CallType.VOICE);

        startActivity(intent);

    }

    public void startVideoCall() {
        // 语音通话
        Intent intent = new Intent(ChatFragment.this.getActivity(), VideoCallActivity.class);
        // 设置被呼叫方 username
        toChatUsername = fragmentArgs.getString(EaseConstant.EXTRA_USER_ID);
        CallManager.getInstance().setChatId(toChatUsername);
        // 设置通话为自己呼出的
        CallManager.getInstance().setInComingCall(false);
        // 设置当前通话类型为视频通话
        CallManager.getInstance().setCallType(CallManager.CallType.VIDEO);

        startActivity(intent);
    }
}


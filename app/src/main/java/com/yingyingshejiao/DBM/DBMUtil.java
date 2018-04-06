package com.yingyingshejiao.DBM;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yingyingshejiao.JavaBean.User;
import com.yingyingshejiao.JavaBean.User_Relative;

import java.util.Date;
import java.util.List;

import static com.yingyingshejiao.DBM.DBM.RelativeDBTable;
import static com.yingyingshejiao.DBM.DBM.RegisterDBTable;
//import static DBM.ImageDBTable;
import static java.util.Arrays.asList;

/**
 * Created by ScoutB on 2018/3/13.
 */
//所有数据库操作在这里
public class DBMUtil {
    private static SQLiteDatabase db;
    private static String TAG = "DBMUtilTAG";
    private static int max_friend = 150;
    private static DBM openHelper;

    //Private
    //获取数据库里条目个数
    private static int count(Context context, String dbName) {
        DBMOperate(context);
        Cursor cursor = db.rawQuery("select count(*) from " + dbName, null);
        int i = 0;
        while (cursor.moveToFirst()) {
            i = cursor.getInt(0);
            break;
        }
        cursor.close();
        return i;
    }

    //当前时间
    private static String CurrentTime(){
        Date date = new Date();
        String rt = date.toString();
        return rt;
    }


    private static void DBMOperate(Context context){
        openHelper = DBM.getDBInstance(context);
        db= openHelper.getWritableDatabase();
    }

    //判断好友关系是否已经存在（是否在一个组里）
    private static boolean FriendAlreadyAdded(String user1, String user2){
        //在此之前按理说已经初始化数据库，如果出错请适当修改
        Cursor cursor0=db.rawQuery("select * from "+RelativeDBTable+" where user_friendname=?", new String[]{user1});
        Cursor cursor1=db.rawQuery("select * from "+RelativeDBTable+" where user_friendname=?", new String[]{user2});
        StringBuffer b0 = new StringBuffer();
        StringBuffer b1 = new StringBuffer();
        while (cursor0.moveToFirst()){
            b0.append(cursor0.getString(cursor0.getColumnIndex("user_friendgroup")));
            b0.append(",");
        }
        while (cursor1.moveToFirst()){
            b1.append(cursor1.getString(cursor1.getColumnIndex("user_friendgroup")));
            b1.append(",");
        }
        String returnBuffer0 = b0.toString();
        List<String> returnList0 = asList(returnBuffer0.split(","));
        String returnBuffer1 = b1.toString();
        List<String> returnList1 = asList(returnBuffer1.split(","));
        for(String att0: returnList0){
            for(String att1: returnList1){
                if(att0.equals(att1)){
                    return true;
                }
            }
        }
        return false;
    }
    //判断用户名是否已经存在
    private static boolean UserAlreadyExisted(String username){
        Cursor cursor=db.rawQuery("select * from "+RegisterDBTable+" where user_name=?", new String[]{username});
        while(cursor.moveToFirst()){
            if(username.equals(cursor.getString(cursor.getColumnIndex("user_name")))){
                cursor.close();
                return true;
            }
        }
        cursor.close();
        return false;
    }

    //Public接口
    //增加特定账户，返回当前账户的id
    public static void addUser(Context context, User user){
        DBMOperate(context);
        if(!UserAlreadyExisted(user.getUser_name())) {
            db.execSQL("insert into "+RegisterDBTable+" (user_name, user_nickname, user_image, user_sex, user_sign) values(?,?,?,?,?)",
                    new String[]{user.getUser_name(), user.getUser_nickname(), user.getUser_image(), user.getUser_sex(), user.getUser_sign()});
        }
        else{
            Log.i(TAG, "Username already exists");
        }
    }

    //增加朋友关系
    public static void addFriend(Context context, User user, User_Relative relative){
        DBMOperate(context);
        //if(FriendAlreadyAdded(user.getUser_name(), relative.getUser_friendname())){
          //  Log.i(TAG, "Friends already in database.");
        //}
        //else{
            db.beginTransaction();
            try {
                db.execSQL("insert into " + RelativeDBTable + " (user_friendname, user_friendgroup) values(?,?)"
                        , new String[]{relative.getUser_friendname(), relative.getUser_friendgroup()});
                db.setTransactionSuccessful();
            }finally {
                db.endTransaction();
            }
        //}
    }

    public static void deleteFriend(Context context, User_Relative relative) {
        DBMOperate(context);
        db.beginTransaction();
        try {
            db.execSQL("delete from " + RelativeDBTable + " where user_friendname=? and user_friendgroup=?"
                    , new String[]{relative.getUser_friendname(), relative.getUser_friendgroup()});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    //返回对应group id下的所有好友名称
    public static List<String> findAllFriend(Context context, User_Relative user_relative){
        DBMOperate(context);
        StringBuffer FriendListA=new StringBuffer();
        Cursor cursor=db.rawQuery("select * from "+RelativeDBTable+" where user_friendgroup=?", new String[]{user_relative.getUser_friendgroup()});
        while(cursor.moveToFirst()) {
            FriendListA.append(cursor.getString(cursor.getColumnIndex("user_friendname")));
            FriendListA.append(",");
        }
        String returnBuffer = FriendListA.toString();
        List<String> returnList = asList(returnBuffer.split(","));
        return returnList;
    }



    //用户查询
    public static User query(Context context,String user_name){
        DBMOperate(context);
        Cursor cursor=db.rawQuery("select * from "+RegisterDBTable+" where user_name=?", new String[]{user_name});
        while(cursor.moveToFirst()){
            String user_name0=cursor.getString(cursor.getColumnIndex("user_name"));
            String user_nickname0=cursor.getString(cursor.getColumnIndex("user_nickname"));
            String user_image0=cursor.getString(cursor.getColumnIndex("user_image"));
            String user_sex0=cursor.getString(cursor.getColumnIndex("user_sex"));
            String user_sign0=cursor.getString(cursor.getColumnIndex("user_sign"));
            User rtUser = new User(user_name0, user_nickname0, user_image0, user_sex0, user_sign0);
            return rtUser;
        }
        cursor.close();
        return null;
    }

    //账户修改
    public static void update(Context context, User user){
        DBMOperate(context);
        db.beginTransaction();
        try {
            db.execSQL("update "+RegisterDBTable+" set user_name=?,user_nickname=?,user_image=?,user_sex=?,user_sign=? where user_name=?",
                    new String[]{user.getUser_name(), user.getUser_nickname(), user.getUser_image(), user.getUser_sex(), user.getUser_sign(), user.getUser_name()});
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    //账户删除
    public static void delete(Context context,String user_name){
        DBMOperate(context);
        db.execSQL("delete from "+RegisterDBTable+" where user_name=?",new String[]{user_name});
    }
}

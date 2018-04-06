package com.yingyingshejiao.DBM;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by ScoutB on 2018/3/12.
 */

//初始化数据库的类，基本可以无视，注册数据库table名register_t，好友数据库table名friends_t
public class DBM extends SQLiteOpenHelper{
    protected static String DBName = "Register_Log00.db";
    public static String RegisterDBTable = "User";
    public static String RelativeDBTable = "User_Relative";
    public static String GroupDBTable = "Group_Relative";
    public static String ChatDBTable = "ChatMessage";
    //public static String ImageDBTable = "image_t";
    protected static int version=1;
    public static DBM dbm;

    public DBM(Context context) {
        super(context, DBName, null, version);
    }

    static synchronized DBM getDBInstance(Context context) {
        if (dbm == null) {
            dbm = new DBM(context);
        }
        return dbm;
    }

    //该方法没有数据库存在才会执行
    public void onCreate(SQLiteDatabase db) {
        Log.i("Log","Creating database.....");
        String sql_message = "create table"+" "+RegisterDBTable+" "
                +"(user_name varchar(30) primary key, user_nickname varchar(20) not null, user_image varchar(30) not null, user_sex varchar(4), user_sign varchar(200))";
        String sql_message0 = "create table"+" "+RelativeDBTable+" "
                +"(user_friendname varchar(30) primary key, user_friendgroup varchar(30) not null)";
        String sql_message1 = "create table"+" "+GroupDBTable+" "
                +"(Group_id varchar(30) primary key)";
        String sql_message2 = "create table"+" "+ChatDBTable+" "
                +"(friend_name varchar(30) primary key, chat_msg_content varchar(200) not null, char_msg_time datetime not null)";
        //String sql_message1 = "create table"+" "+ImageDBTable+" "+"(id int primary key, name varchar(50), avatar BLOB)";
        db.execSQL(sql_message);
        db.execSQL(sql_message0);
        db.execSQL(sql_message1);
        db.execSQL(sql_message2);
    }

    //数据库存更新才会执行
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("updateLog","Database updated...");
    }
}

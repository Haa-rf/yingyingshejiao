package com.example.DBM;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by ScoutB on 2018/3/12.
 */

//初始化数据库的类，基本可以无视，注册数据库table名register_t，好友数据库table名friends_t
public class DBM extends SQLiteOpenHelper{
    protected static String DBName = "Register_Log.db";
    public static String RegisterDBTable = "register_t";
    public static String FriendDBTable = "friends_t";
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
        String sql_message = "create table"+" "+RegisterDBTable+" "+"(id int primary key,username varchar(50),pwd varchar(50),datetime varchar(50))";
        String sql_message0 = "create table"+" "+FriendDBTable+" "+"(id int primary key, id_f int, datetime varchar(50))";
        db.execSQL(sql_message);
        db.execSQL(sql_message0);
    }

    //数据库存更新才会执行
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("updateLog","Database updated...");
    }

}


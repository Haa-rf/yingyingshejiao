package com.example.DBM;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.drm.DrmStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.User.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static com.example.DBM.DBM.FriendDBTable;
import static com.example.DBM.DBM.RegisterDBTable;
import static java.util.Arrays.asList;

/**
 * Created by ScoutB on 2018/3/13.
 */
//所有数据库操作在这里
public class DBMUtil {
    private static SQLiteDatabase db;
    private static String TAG = "DBMUtilTAG";
    private static int max_friend = 150;

    //Private
    //获取数据库里条目个数
    private static int count(Context context, String dbName){
        DBMOperate(context);
        Cursor cursor=db.rawQuery("select count(*) from "+dbName, null);
        int i = 0;
        while(cursor.moveToFirst()){
            i=cursor.getInt(0);
            break;
        }
        cursor.close();
        if(dbName.equals(FriendDBTable)){return i/2;}
        return i;
    }

    //获取最大id
    private static int getMaxId(Context context, String dbName){
        DBMOperate(context);
        int maxId=-1;
        Cursor cursor=db.rawQuery("select max(id) AS maxId from "+dbName, null);
        if(0 == cursor.getCount()) {
            return 0;
        }
        else {
            if(cursor.moveToFirst()) {
                maxId = cursor.getInt(cursor.getColumnIndex("maxId"));
            }
                return maxId;
        }
    }

    //当前时间
    private static String CurrentTime(){
        Date date = new Date();
        String rt = date.toString();
        return rt;
    }

    //加密部分，提供可选加密手段
    private static String Encryption(String pwd){
        // Hash a password for the first time
        //String hashed = BCrypt.hashpw(pwd, BCrypt.gensalt());

        // gensalt's log_rounds parameter determines the complexity
        // the work factor is 2**log_rounds, and the default is 10
        String hashed2 = BCrypt.hashpw(pwd, BCrypt.gensalt(12));
        return hashed2;
    }

    private static boolean Checkpwd(String pwd, String hashed){
        return BCrypt.checkpw(pwd, hashed);
    }

    private static void DBMOperate(Context context){
        DBM openHelper = DBM.getDBInstance(context);
        db= openHelper.getWritableDatabase();
    }

    //判断好友关系是否已经存在
    private static boolean FriendAlreadyAdded(int id, int id_f){
        //在此之前按理说已经初始化数据库，如果出错请适当修改
        Cursor cursor=db.rawQuery("select * from "+FriendDBTable+" where id=?", new String[]{String.valueOf(id)});
        while(cursor.moveToFirst()){
            if(id_f == cursor.getInt(cursor.getColumnIndex("id"))){
                cursor.close();
                return true;
            }
        }
        cursor.close();
        return false;
    }
    //判断用户名是否已经存在
    private static boolean UserAlreadyExisted(String username){
        Cursor cursor=db.rawQuery("select * from "+RegisterDBTable+" where username=?", new String[]{username});
        while(cursor.moveToFirst()){
            if(username.equals(cursor.getString(cursor.getColumnIndex("username")))){
                cursor.close();
                return true;
            }
        }
        cursor.close();
        return false;
    }

    //Public接口
    //增加特定账户，返回当前账户的id
    public static int addUser(Context context, User user){
        DBMOperate(context);
        String date = CurrentTime();
        if(!UserAlreadyExisted(user.getUsername())) {
            db.execSQL("insert into "+RegisterDBTable+" (id, username, pwd, datetime) values(?,?,?,?)",
                    new Object[]{String.valueOf(getMaxId(context, RegisterDBTable) + 1), user.getUsername(), user.getPwd(), date});
            return getMaxId(context, RegisterDBTable) - 1;
        }
        else{
            Log.i(TAG, "Username already exists");
            return -1;
        }
    }

    //增加朋友关系，一次增加双向，占用更大磁盘空间但是显著降低运行时间
    public static void addFriend(Context context, User user1, User user2){
        DBMOperate(context);
        String date = CurrentTime();
        if(FriendAlreadyAdded(user1.getId(), user2.getId())){
            Log.i(TAG, "Friends already in database.");
        }
        else{
            db.beginTransaction();
            try {
                db.execSQL("insert into " + FriendDBTable + " (id, id_f, datetime) values(?,?,?)",
                        new Object[]{String.valueOf(user1.getId()), String.valueOf(user2.getId()), date});
                db.execSQL("insert into " + FriendDBTable + " (id, id_f, datetime) values(?,?,?)",
                        new Object[]{String.valueOf(user2.getId()), String.valueOf(user1.getId()), date});
                db.setTransactionSuccessful();
            }finally {
                db.endTransaction();
            }
        }
    }

    public static void deleteFriend(Context context, User user1, User user2){
        DBMOperate(context);
        if(!FriendAlreadyAdded(user1.getId(),user2.getId())){
            Log.i(TAG, "Friends relation not exists.");
        }
        else{
            db.beginTransaction();
            try{
                db.execSQL("delete from "+FriendDBTable+" where id=? and id_f=",
                        new Object[]{String.valueOf(user1.getId()), String.valueOf(user2.getId())});
                db.execSQL("delete from "+FriendDBTable+" where id=? and id_f=",
                        new Object[]{String.valueOf(user2.getId()), String.valueOf(user1.getId())});
                db.setTransactionSuccessful();
            }finally {
                db.endTransaction();
            }
        }
    }

    //返回对应id下账户的好友名称
    public static List<String> findAllFriend(Context context, int id){
        DBMOperate(context);
        StringBuffer FriendListA=new StringBuffer();
        Cursor cursor=db.rawQuery("select * from "+FriendDBTable+" where id=?", new String[]{String.valueOf(id)});
        while(cursor.moveToFirst()){
            int id_f=cursor.getInt(cursor.getColumnIndex("id_f"));
            Cursor cursor1=db.rawQuery("select username from "+RegisterDBTable+" where id=?", new String[]{String.valueOf(id_f)});
            while(cursor1.moveToFirst()){
                FriendListA.append(cursor1.getString(cursor1.getColumnIndex("username")));
                FriendListA.append(",");
            }
        }
        String returnBuffer = FriendListA.toString();
        List<String> returnList = asList(returnBuffer.split(","));
        return returnList;
    }

    //用户查询
    public static User query(Context context,int id){
        DBMOperate(context);
        Cursor cursor=db.rawQuery("select * from "+RegisterDBTable+" where id=?", new String[]{String.valueOf(id)});
        while(cursor.moveToFirst()){
            String name=cursor.getString(cursor.getColumnIndex("username"));
            String pwd=cursor.getString(cursor.getColumnIndex("pwd"));
            User rtUser = new User();
            rtUser.setUsername(name);
            rtUser.setPwd(pwd);
            return rtUser;
        }
        cursor.close();
        return null;
    }

    public static User query(Context context, String user){
        DBMOperate(context);
        Cursor cursor=db.rawQuery("select * from "+RegisterDBTable+" where username=?", new String[]{user});
        while(cursor.moveToFirst()){
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String pwd=cursor.getString(cursor.getColumnIndex("pwd"));
            User rtUser = new User();
            rtUser.setId(id);
            rtUser.setPwd(pwd);
            return rtUser;
        }
        cursor.close();
        return null;
    }

    //账户修改
    public static void update(Context context, User user, int id){
        DBMOperate(context);
        String date = CurrentTime();
        db.beginTransaction();
        try {
            db.execSQL("update "+RegisterDBTable+" set username=?,pwd=?,datetime=? where id=?",
                    new Object[]{user.getUsername(), user.getPwd(), date, String.valueOf(id)});
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    //账户删除
    public static void delete(Context context,int id){
        DBMOperate(context);
        db.execSQL("delete from "+RegisterDBTable+" where id=?",new Object[]{String.valueOf(id)});
    }
}

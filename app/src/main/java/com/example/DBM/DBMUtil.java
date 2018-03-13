package com.example.DBM;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.User.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ScoutB on 2018/3/13.
 */
//所有数据库操作在这里
public class DBMUtil {
    private static DBM openHelper;
    private static SQLiteDatabase db;

    //Private
    private static int count(Context context){
        openHelper=DBM.getDBInstance(context);
        db=openHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select count(*) from register_t", null);
        int i = 0;
        while(cursor.moveToFirst()){
            i=cursor.getInt(0);
            break;
        }
        cursor.close();
        return i;
    }

    private static String CurrentTime(){
        Date date = new Date();
        String rt = date.toString();
        return rt;
    }

    //Public接口
    public static void add(Context context, User user){
        openHelper=DBM.getDBInstance(context);
        db=openHelper.getWritableDatabase();
        String date = CurrentTime();
        db.execSQL("insert into register_t (id, username, pwd, datetime) values(?,?,?,?)",
                new Object[]{count(context), user.getUsername(), user.getPwd(), date});

    }

    public static User query(Context context,int id){
        openHelper=DBM.getDBInstance(context);
        db=openHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from register_t where personid=?", new String[]{String.valueOf(id)});
        while(cursor.moveToFirst()){
            int personid=cursor.getInt(cursor.getColumnIndex("id"));
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

    public static void update(Context context, User user, int id){
        openHelper=DBM.getDBInstance(context);
        db=openHelper.getWritableDatabase();
        String date = CurrentTime();
        db.execSQL("update register_t set username=?,pwd=?,datetime=? where id=?",
                new Object[]{user.getUsername(),user.getPwd(),date,String.valueOf(id)});
    }

    public static void delete(Context context,int id){
        openHelper=DBM.getDBInstance(context);
        db=openHelper.getWritableDatabase();
        db.execSQL("delete from register_t where id=?",new Object[]{String.valueOf(id)});
    }
}

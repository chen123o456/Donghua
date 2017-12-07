package us.mifeng.contentprovider03.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 黑夜之火 on 2017/12/5.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static DbHelper instance;
    public DbHelper(Context context) {
        super(context, SQLiteInfo.SQLTABLE, null, SQLiteInfo.VERSION);
    }
    //因为我们在使用数据的时候 ，反复的使用，
    // 所以我们将DBHelper对象封装成单利
    public static DbHelper getInstance(Context context){
        if (instance ==null){
            synchronized (DbHelper.class){
                if (instance == null){
                    instance = new DbHelper(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表
        String sql = "create table if not exists "+
                SQLiteInfo.TABLE +"("+SQLiteInfo._ID+"" +
                " integer primary key autoincrement, "+
                SQLiteInfo.NAME +" text not null, "+
                SQLiteInfo.AGE +" integer )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

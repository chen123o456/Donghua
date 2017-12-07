package com.example.provide;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by 黑夜之火 on 2017/12/1.
 */

public class MyProvider extends ContentProvider {

    private SQLiteDatabase db;
    //声明查询的权限
    private static String authority = "vn.us.mifeng";
    //声明三个变量用来标识用户根据什么去查询
    //查询所有的
    private static int USER = 0;
    //根据id进行查询
    private static int USER_ID = 1;
    //根据内容进行查询
    private static int USER_BODY = 2;
    //设置匹配对象
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    //添加匹配的值
    static {
        // select * from user
        matcher.addURI(authority,"user",USER);
        // select * from user where _id = 'id';
        matcher.addURI(authority,"user/#",USER_ID);
        //select * from user where body = 'body'
        matcher.addURI(authority,"user/*",USER_BODY);
    }
    //创
    // 建provider
    @Override
    public boolean onCreate() {
        DbHelper helper = new DbHelper(getContext());
        //创建对数据表的读写对象
        db = helper.getWritableDatabase();
        return false;
    }
    //查询
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        //开始查询
        //获取查询要匹配的值,根据值进行判断查询的方式
        int code = matcher.match(uri);
        Log.i("tag","============="+code);
        switch (code){
            case 0:
                cursor = db.query("user",null,null,null,null,null,null);
                break;
            case 1:
                //第一步获取id
                long id =  ContentUris.parseId(uri);
                cursor = db.query("user",null,
                        "_id  = ?",new String[]{id+""},null,null,null);
                break;
            case 2:
                //获取路径之后的变量
                String body = uri.getLastPathSegment();
                cursor = db.query("user",null,"body = ?",new String[]{body+""},null,null,null);
                break;
        }

        return cursor;
    }

    //查询的类型
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        return "   ";
    }

    //插入
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long l = db.insert("user",null,values);
        Uri newUri = null;
        if (l>0){
            newUri = ContentUris.withAppendedId(uri,l);
            Log.i("tag","数据插入成功");
        }else{
            Log.i("tag","数据插入失败");
        }
        return newUri;
    }
    //删除
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int code = matcher.match(uri);
        //你删除的记录
        int num = -1;
        switch (code){
            case 0://删除所有的数据
                num =  db.delete("user",null,null);
                break;
            case 1: //根据id对数据进行删除
                long id = ContentUris.parseId(uri);
                num = db.delete("user","_id =?",new String[]{id+""});
                break;
            case 2: //根据关键字进行删除
                String body = uri.getLastPathSegment();
                num = db.delete("user","body = ?",new String[]{body});
                break;
        }
        return num;
    }
    //更新
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        //因为我们数据库中只存放了id和body，又因为id主键，无法更改，所以，在这儿只能根据id更改body
        //long id = ContentUris.parseId(uri);
        //int num = db.update("user",values,"_id = ?",new String[]{id+""});
        int num = db.update("user",values,selection,selectionArgs);
        return num;
    }
}

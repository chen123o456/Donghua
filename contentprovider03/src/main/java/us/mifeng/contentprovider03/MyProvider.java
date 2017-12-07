package us.mifeng.contentprovider03;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import us.mifeng.contentprovider03.db.DbHelper;
import us.mifeng.contentprovider03.db.SQLiteInfo;

public class MyProvider extends ContentProvider {
    private static String authority = "mifeng.us";
    private static int USER = 0;
    private static int ID = 1;
    private static int NAME = 2;
    //设置匹配的对象
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        //查询所有的
        matcher.addURI(authority,"user",USER);
        matcher.addURI(authority,"user/#",ID);
        matcher.addURI(authority,"user/*",NAME);
    }

    private SQLiteDatabase db;

    public MyProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int type = matcher.match(uri);
        int delete = -1;
        switch (type){
            case 0:
                //删除所有的信息
                delete = db.delete(SQLiteInfo.TABLE,null,null);
                break;
            case 1:
                //根据id进行删除
                long id = ContentUris.parseId(uri);
                delete = db.delete(SQLiteInfo.TABLE,SQLiteInfo._ID + " = ?",new String[]{id+""});
                break;
            case 2:
                //根据用户名删除
                String name = uri.getLastPathSegment();
                delete = db.delete(SQLiteInfo.TABLE,SQLiteInfo.NAME + "= ?",new String[]{name});
                break;
        }
        return delete;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    //插入数据
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        long id = db.insert(SQLiteInfo.TABLE, null, values);
        if (id>0){
            Uri newUri = ContentUris.withAppendedId(uri, id);
            return newUri;
        }
        return null;

    }

    @Override
    public boolean onCreate() {
        DbHelper helper = DbHelper.getInstance(getContext());
        db = helper.getReadableDatabase();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        //根据uri解析出查询的条件
        int type = matcher.match(uri);
        switch (type){
            case 0:
                //查询所有的
                cursor = db.query(SQLiteInfo.TABLE,null,null,null,null,null,null);
                break;
            case 1:
                //根据id进行查询
                long id = ContentUris.parseId(uri);
                cursor = db.query(SQLiteInfo.TABLE,null,SQLiteInfo._ID +" = ?",
                        new String[]{id+""},null,null,null);
                break;
            case 2:
                //根据指定的值进行查询
                String name = uri.getLastPathSegment();
                cursor = db.query(SQLiteInfo.TABLE,null,SQLiteInfo.NAME + " = ?",
                        new String[]{name},null,null,null);
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int update = db.update(SQLiteInfo.TABLE, values, selection, selectionArgs);
        return update;
    }
}

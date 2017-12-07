package com.example.providerresolver;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private String path = "content://vn.us.mifeng/user/嘿1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取接收这对象
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(Uri.parse(path), null, null, null, null, null);
        Log.i("tag","===cursor"+cursor);
        //if (cursor!=null){
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String body = cursor.getString(cursor.getColumnIndex("body"));
            Log.i("tag","======body="+body+"   id="+id);
        }
        //  }
    }
}

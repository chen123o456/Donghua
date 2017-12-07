package com.example.provide;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DbHelper helper = new DbHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        //创建了添加数据的对象
        ContentValues values = new ContentValues();
        for(int i=0;i<5;i++){
            values.put("body","嘿"+i);
            db.insert("user",null,values);
            values.clear();
        }
        db.close();
    }
}

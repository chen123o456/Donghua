package com.example.content02;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    //定义路径，打开通话记录的路径
    private String callLogs = "content://call_log/calls";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        //接收这对象
        ContentResolver resolver = getContentResolver();
        //创建Cursor对象
        Cursor cursor = resolver.query(Uri.parse(callLogs),null,null,null,null);
        //创建游标适配器
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                //上下文
                this,
                //布局               
                R.layout.call_item,
                cursor,
                //查询的字段
                new String[]{CallLog.Calls.CACHED_NAME,
                        CallLog.Calls.NUMBER,
                        CallLog.Calls.CACHED_NUMBER_TYPE,
                        CallLog.Calls.DATE},

                //控件的id
                new int[]{R.id.name,R.id.number,R.id.numberType,R.id.date},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );

        listView.setAdapter(adapter);

    }
}

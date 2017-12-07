package com.example.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/12/6.
 */

public class MyReceiver3 extends BroadcastReceiver {
    //广播接收到消息之后会自动调用该方法
    @Override
    public void onReceive(Context context, Intent intent) {
        //处理发布者发布的数据，
        String str = intent.getStringExtra("str");
        Toast.makeText(context,str+"receiver3",Toast.LENGTH_SHORT).show();
        setResultData("我是改变之后的数据");
    }
}

package com.example.receiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static android.R.attr.filter;

public class MainActivity extends AppCompatActivity {

    private MyReceiver receiver1;
    private MyReceiver2 receiver2;
    private MyReceiver3 receiver3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void send(View view) {
        Intent intent = new Intent();
        intent.putExtra("str","我是来自Activity当中的数据");
        intent.setAction("us.buba");
        //发送广播
        sendBroadcast(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //动态注册广播
        receiver1 = new MyReceiver();
        receiver2 = new MyReceiver2();
        receiver3 = new MyReceiver3();

        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("us.buba");
        registerReceiver(receiver1,filter1);

        IntentFilter filter2 = new IntentFilter("us.buba");
        filter2.setPriority(100);
        registerReceiver(receiver2,filter2);

        IntentFilter filter3 = new IntentFilter("us.buba");
        filter3.setPriority(50);
        registerReceiver(receiver3,filter3);
    }



    @Override
    protected void onPause() {
        super.onPause();

        if (receiver1!=null){
            unregisterReceiver(receiver1);
        }
        if (receiver2!=null){
            unregisterReceiver(receiver2);
        }
        if (receiver3 != null){
            unregisterReceiver(receiver3);
        }
    }

}

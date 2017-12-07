package com.example.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017/12/6.
 */

public class MyReceiver2 extends BroadcastReceiver {
    //广播接收到消息之后会自动调用该方法
    @Override
    public void onReceive(Context context, Intent intent) {
        //发送通知
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //创建通知对象
        Notification.Builder builder = new Notification.Builder(context);
        //通知必须要三个属性
        builder.setContentTitle("你中奖了");
        builder.setContentText("恭喜你中了5000万，请输入邀请码去官网兑现");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        manager.notify(0x11,builder.build());
    }
}

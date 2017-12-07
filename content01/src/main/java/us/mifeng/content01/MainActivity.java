package us.mifeng.content01;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    //短信暴露的路径先拿到
    private String path = "content://sms/";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        //学习游标适配器
        //获取游标对象，首先获取广播接收者对象
        ContentResolver resolver = getContentResolver();
        //根据广播的接收者对象去获取游标
        Cursor cursor = resolver.query(Uri.parse(path),null,null,null,null,null);
        MyCursorAdapter adapter = new MyCursorAdapter(this,cursor,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        listView.setAdapter(adapter);
    }
    //创建游标适配器 CursorAdapter
    class MyCursorAdapter extends CursorAdapter{

        public MyCursorAdapter(Context context, Cursor c, int autoRequery) {
            super(context, c, autoRequery);
        }

        //创建视图，并且返回
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.sms_item,null);
            ViewHolder vh = new ViewHolder(view);
            view.setTag(vh);
            return view;
        }
        //讲数据绑定到视图上
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            ViewHolder vh = (ViewHolder) view.getTag();
            String address = cursor.getString(cursor.getColumnIndex("address"));
            vh.address.setText(address);

            int date = cursor.getInt(cursor.getColumnIndex("date"));
            //时间匹配类
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dd = new Date(date);
            String dateValue = format.format(dd);
            vh.date.setText(dateValue);

            int type = cursor.getInt(cursor.getColumnIndex("type"));
            String typeValue = "";
            if (type==1){
                typeValue ="接收";
            }else{
                typeValue = "发送";
            }
            vh.send.setText(typeValue);

            String body = cursor.getString(cursor.getColumnIndex("body"));
            vh.body.setText(body);
        }
        class ViewHolder{
            TextView address,date,send,body;

            public ViewHolder(View view) {
                address = (TextView) view.findViewById(R.id.address);
                date = (TextView) view.findViewById(R.id.date);
                send = (TextView) view.findViewById(R.id.send);
                body = (TextView) view.findViewById(R.id.body);
            }
        }
    }
}

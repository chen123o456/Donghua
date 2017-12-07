package us.mifeng.contentprovider03;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import us.mifeng.contentprovider03.db.DbHelper;
import us.mifeng.contentprovider03.db.SQLiteInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //向数据库中先插入两条测试数据
        SQLiteDatabase db = DbHelper.getInstance(this).getWritableDatabase();
        ContentValues values = new ContentValues();
        for(int i =0;i<5;i++){
            values.clear();
            values.put(SQLiteInfo.NAME,"张三"+i);
            values.put(SQLiteInfo.AGE,"2"+i);
            db.insert(SQLiteInfo.TABLE,null,values);
        }
    }
}

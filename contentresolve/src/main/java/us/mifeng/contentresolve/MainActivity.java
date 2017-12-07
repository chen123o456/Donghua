package us.mifeng.contentresolve;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button query;
    private Button insert;
    private Button update;
    private Button delete;
    private ContentResolver resolver;

    private String path = "content://mifeng.us/user/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resolver = getContentResolver();
        initView();

    }

    private void initView() {
        query = (Button) findViewById(R.id.query);
        insert = (Button) findViewById(R.id.insert);
        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);
        query.setOnClickListener(new MyClickListener());
        insert.setOnClickListener(new MyClickListener());
        update.setOnClickListener(new MyClickListener());
        delete.setOnClickListener(new MyClickListener());
    }

    private class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.query:
                    queryData();
                    break;
                case R.id.insert:
                    insertData();
                    break;
                case R.id.update:
                    updateData();
                    break;
                case R.id.delete:
                    deleteData();
                    break;
            }
        }

    }

    private void deleteData() {

        int delete = resolver.delete(Uri.parse(path+"/'小胖'"), SQLiteInfo.NAME + "= ?",
                new String[]{"小胖"});
        if (delete>0){
            queryData();
        }
    }

    private void updateData() {
        ContentValues values = new ContentValues();
        values.put(SQLiteInfo.NAME,"小胖");
        values.put(SQLiteInfo.AGE,21);
        int update = resolver.update(Uri.parse(path), values,
                SQLiteInfo._ID + " = ?", new String[]{6 + ""});
        if (update>0){
            queryData();
        }
    }


    private void insertData() {
        ContentValues values = new ContentValues();
        values.put(SQLiteInfo.NAME,"李四");
        values.put(SQLiteInfo.AGE,20);
        Uri insert = resolver.insert(Uri.parse(path), values);
        if (insert!=null){
            queryData();
        }
    }
    private void queryData() {
        Cursor cursot = resolver.query(Uri.parse(path), null, null, null, null, null);
        if (cursot!=null){
            while (cursot.moveToNext()){
                int id = cursot.getInt(cursot.getColumnIndex(SQLiteInfo._ID));
                String name = cursot.getString(cursot.getColumnIndex(SQLiteInfo.NAME));
                int age = cursot.getInt(cursot.getColumnIndex(SQLiteInfo.AGE));
                Log.i("tag","====id="+id+"  name="+name+"  age="+age);
                //将数据存放到List集合中，然后显示到ListView上
            }
        }

    }
}

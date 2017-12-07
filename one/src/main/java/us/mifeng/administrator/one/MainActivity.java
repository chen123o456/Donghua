package us.mifeng.administrator.one;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import java.util.ArrayList;

import us.mifeng.administrator.test.R;

public class MainActivity extends AppCompatActivity implements OkHttp.DownData{
    private ArrayList<JsonBea.DataBean.ListBean>list=new ArrayList<>();
    private PullToRefreshListView listView;
    private MyAdapter adapter;
    private String path = "http://192.168.190.188/Goods/app/item/list.json?curPage=";
    int curPage=1;
    private OkHttp utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        utils = new OkHttp(this,this);
        utils.getResponse(path+String.valueOf(curPage));
    }

    private void initView() {
        listView = (PullToRefreshListView) findViewById(R.id.listview);
        adapter = new MyAdapter(list,this);
        listView.setAdapter(adapter);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                curPage++;
                utils.getResponse(path+String.valueOf(curPage));
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                curPage++;
                utils.getResponse(path+String.valueOf(curPage));
            }
        });
    }

    @Override
    public void onResult(String json) {
        listView.onRefreshComplete();
        if (json!=null){
            JsonBea bean=new JsonUtils().getData(json);
            ArrayList<JsonBea.DataBean.ListBean>al= (ArrayList<JsonBea.DataBean.ListBean>) bean.getData().getList();
            list.addAll(al);
            if (list!=null){
                adapter.notifyDataSetChanged();
            }
        }
    }
}

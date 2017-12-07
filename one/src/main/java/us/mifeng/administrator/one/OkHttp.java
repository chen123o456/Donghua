package us.mifeng.administrator.one;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.attr.id;

/**
 * Created by Administrator on 2017/11/27.
 */

public class OkHttp {
    private Context context;
    private OkHttpClient instance;
    private DownData load;
    private final Handler handler;

    public OkHttp(Context context, DownData id) {
        this.context = context;
        this.load=load;
        handler = new Handler();
    }
    public boolean isOk(){
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        if (info.getState().equals(NetworkInfo.State.CONNECTED)){
            return true;
        }
        return false;
    }
    public OkHttpClient getClient(){
        if (instance==null){
            synchronized (this){
                if (instance==null){
                    instance=new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).connectTimeout(5,TimeUnit.SECONDS).build();
                }
            }
        }
        return instance;
    }
    public void getResponse(String path){
        if (!isOk()){
            return;
        }
        OkHttpClient client=getClient();
        Request request=new Request.Builder().url(path).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String s=response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        load.onResult(s);
                    }
                });
            }
        });
    }
    public interface DownData{
        void onResult(String json);
    }
}

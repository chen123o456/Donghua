package us.mifeng.imagecache;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 黑夜之火 on 2017/11/29.
 */

public class HttpUtils {
    //判断网络是否连接成功
    public static boolean isConnected(Context context){
        //创建链接对象
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取网络连接的信息
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info!=null){
            return info.isConnected();
        }
        return false;
    }
    //写一个网络请求去下载数据
    public byte[]getRequest(Context context,String path){
        //下载网络数据
        if (!isConnected(context)){
            //网络连接失败
            return null;
        }
        //创建资源位置
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(false);
            //判断服务器是否请求成功
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream inputStream = conn.getInputStream();
                int len = 0;
                byte[]by = new byte[1024];
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                while((len=inputStream.read(by))!=-1){
                    bos.write(by,0,len);
                }
                byte[] bytes = bos.toByteArray();
                bos.close();
                inputStream.close();
                return bytes;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

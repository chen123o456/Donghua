package us.mifeng.administrator.one;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/11/27.
 */

public class JsonUtils {
    public JsonBea getData(String json){
        Gson gson=new Gson();
        JsonBea bean=gson.fromJson(json,JsonBea.class);
        return bean;
    }
}

package us.mifeng.administrator.one;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import us.mifeng.administrator.test.R;

/**
 * Created by Administrator on 2017/11/27.
 */

public class MyAdapter extends BaseAdapter {
    private ArrayList<JsonBea.DataBean.ListBean>list;
    private Context context;

    public MyAdapter(ArrayList<JsonBea.DataBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public JsonBea.DataBean.ListBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item,null);
            vh=new ViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        JsonBea.DataBean.ListBean bean=getItem(position);
        vh.text.setText(bean.getTitle());
        Picasso.with(context).load("http://192.168.190.188/Goods/uploads/"+bean.getImage()).into(vh.img);
        return convertView;
    }
    class ViewHolder{
        ImageView img;
        TextView text;

        public ViewHolder(View view) {
            img= (ImageView) view.findViewById(R.id.img);
            text= (TextView) view.findViewById(R.id.text);
        }
    }
}

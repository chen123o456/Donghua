package us.mifeng.imagecache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {
    private String path = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3965705221,2010595691&fm=27&gp=0.jpg";
    private ImageView imageView;
    private String imgName = "img01.jpg";
    //创建一个弱引用，  所有的map集合键不能重复，如果重复就会后面的覆盖前面的值
    //HashMap  非线程安全  可以有一个键为空
    // TreeMap  经过了二叉排序，
    // HashTable  线程安全的  所有的键不能为空
    // LinkedHashMap  有序的存放，插入和删除较快
    private LinkedHashMap<String ,SoftReference<Bitmap>>memory = new LinkedHashMap<>();
    private MyLruCache lruCache;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bitmap bitmap = (Bitmap) msg.obj;
            imageView.setImageBitmap(bitmap);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        //获取手机的运行内存
        int memorySize = (int) Runtime.getRuntime().maxMemory()/8;

        lruCache = new MyLruCache(memorySize);


    }

    public void click(View view) {
        //首先从引用去加载图片
        Bitmap bitmap = getBitmapCache();

        if (bitmap==null){
            MyThread thread = new MyThread();
            thread.start();
        }
    }
    //创建手机的高速缓冲区
    class MyLruCache extends LruCache<String,Bitmap>{

        /**
         * @param maxSize for caches that do not override {@link #sizeOf}, this is
         *                the maximum number of entries in the cache. For all other caches,
         *                this is the maximum sum of the sizes of the entries in this cache.
         *                因为缓存不覆盖{ @link # sizeOf },这是缓存中的条目的最大数量。
         *                对于所有其他缓存,这是最大的和这个cachefor缓存条目的大小不覆盖
         *                { @link # sizeOf },
         *                这是缓存中的条目的最大数量。
         *                对于所有其他缓存,这是最大的和在这个缓存条目的大小
         */
        public MyLruCache(int maxSize) {
            super(maxSize);
        }

        //去计算图片的大小
        @Override
        protected int sizeOf(String key, Bitmap value) {
            //value.getByteCount();
            return value.getRowBytes()*value.getHeight();
        }

        /**
         *
         * @param evicted 驱逐，被驱赶的意思，也就说当内存不足，
         *                要释放一些空间的时候为true,否则为false
         * @param key     图片的名字或者地址
         * @param oldValue 要被移除的图片
         * @param newValue 新添加进去的图片
         */
        @Override
        protected void entryRemoved(boolean evicted,
                                    String key, Bitmap oldValue,
                                    Bitmap newValue) {
            super.entryRemoved(evicted, key, oldValue, newValue);
            if (evicted){
                SoftReference<Bitmap> reference = new SoftReference<Bitmap>(oldValue);
                memory.put(key,reference);
            }
        }
    }
    class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            HttpUtils utils = new HttpUtils();
            byte[] b = utils.getRequest(MainActivity.this, path);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);
            //向引用当中添加一份
            if (bitmap!=null){
                lruCache.put(path,bitmap);
                Message message = handler.obtainMessage();
                ExternalUtils.wreiteData(imgName,b);
                message.obj = bitmap;
                handler.sendMessage(message);
            }
        }
    }
    private Bitmap getBitmapCache(){
        //首先从强引用中去
        Bitmap bitmap = lruCache.get(path);
        if (bitmap==null){
            //从软引用去
            SoftReference<Bitmap> soft = memory.get(path);
            if (soft!=null){
                bitmap = soft.get();
                lruCache.put(path,bitmap);
                //因为图片呢正在被强引用使用，加入了强引用所以要从弱引用移除
                memory.remove(path);
            }else{
                //从本地当中去取
                byte[] bytes = ExternalUtils.readData(imgName);
                if (bytes!=null){
                    bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    if (bitmap!=null){
                        lruCache.put(path,bitmap);
                    }
                }
            }
        }
        return bitmap;
    }
}

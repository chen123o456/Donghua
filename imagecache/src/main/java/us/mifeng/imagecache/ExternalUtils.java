package us.mifeng.imagecache;

import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 黑夜之火 on 2017/11/29.
 */

public class ExternalUtils {
    private static String fileName = "images";
    //判断我们的手机有没有sd卡
    public static boolean isSdCardUser(){
        //获取外部存储变量的状态
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }
    private static  File getRootFile(){
        File sdFile = Environment.getExternalStorageDirectory();
        File fileDir = new File(sdFile,fileName);
        if (!fileDir.exists()){
            fileDir.mkdirs();
        }
        return fileDir;
    }
    public static  void wreiteData(String imgName,byte[]b){
        if (!isSdCardUser()){
            return;
        }
        File fileDir = getRootFile();
        File file = new File(fileDir,imgName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(b,0,b.length);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static byte[]readData(String imgName){
        if (!isSdCardUser()){
            return null;
        }
        File fileDir = getRootFile();
        File file = new File(fileDir,imgName);
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len = 0;
            byte[]by = new byte[1024];
            while((len = fis.read(by))!=-1){
                bos.write(by,0,len);
            }
            byte[]b = bos.toByteArray();
            bos.flush();
            bos.close();
            fis.close();
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

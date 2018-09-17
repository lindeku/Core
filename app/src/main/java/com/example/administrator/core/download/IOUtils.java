package com.example.administrator.core.download;

import android.content.Context;
import android.os.Environment;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2018/8/11 0011.
 */

public class IOUtils {
    public static void closeIO(Closeable... closeables){
        if(closeables!=null){
         for (Closeable c:closeables){
             try {
                 c.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
        }
    }

    public static File clearApk(Context context ,String apkName){
        File apkFile=new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),apkName);
        if(apkFile.exists()){
            apkFile.delete();
        }
        return apkFile;
    }
}

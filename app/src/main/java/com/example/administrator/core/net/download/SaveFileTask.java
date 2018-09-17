package com.example.administrator.core.net.download;

import android.os.AsyncTask;
import android.util.Log;

import com.example.administrator.core.net.callback.ISuccess;
import com.example.administrator.core.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by 林颖 on 2018/5/17.
 *
 */

public class SaveFileTask extends AsyncTask<Object,Void,File> {
    private final ISuccess SUCCESS;
    private SaveFileTask(){
        SUCCESS=null;
    }
    public SaveFileTask(ISuccess SUCCESS) {
        this.SUCCESS = SUCCESS;
    }
    @Override
    protected File doInBackground(Object... objects) {
        String downliadDin= (String) objects[0];
        String extebsion= (String) objects[1];
        String name=(String)objects[2];
        ResponseBody body= (ResponseBody) objects[3];
        InputStream in=body.byteStream();
        if(downliadDin==null||downliadDin.equals("")){
            downliadDin="intermediary";
        }
        if(extebsion==null||extebsion.equals("")){
            extebsion="";
        }
        if(name==null){
            return FileUtil.writeToDisk(in,downliadDin,extebsion.toUpperCase(),extebsion);
        }else {
            return FileUtil.writeToDisk(in,downliadDin,name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if(SUCCESS!=null){
            SUCCESS.onSuccess(file.getPath());
        }
        autoInstallApk(file);
    }
    //下载apk文件自动安装
    private void autoInstallApk(File file){
        if(FileUtil.getExtension(file.getPath()).equals("apk")){
            Log.e("SaveFileTask","autoInstallApk方法");
        }
    }
}

package com.example.administrator.core.download;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Administrator on 2018/8/13 0013.
 */

public class InstallUtil {
    public static void install(Context context,String apkPath,boolean rootMode){
        if(rootMode){
            installRoot(context,apkPath);
        }else {
            installNormal(context,apkPath);
        }

    }
    public static void install(Context context,String apkPath){
        install(context,apkPath,false);
    }

    private static void installNormal(Context context, String apkPath) {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.N){
            File file=new File(apkPath);

            /*   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
            Uri apkUri= FileProvider.getUriForFile(context,"com.ordinary",file);

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }else {

            intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
        }

        context.startActivity(intent);
    }

    private static void installRoot(final Context context, final String apkPath) {
        Observable.just(apkPath)
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return "pm install"+s;
                    }
                })
                .map(new Function<String, Integer>() {
                      @Override
                      public Integer apply(String s) throws Exception {
                        return SystemManager.RootCommand(s);
                      }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                     @Override
                      public void onSubscribe(Disposable d) {

                        }
                     @Override
                     public void onNext(Integer value) {
                             if(value==0){
                                 Toast.makeText(context,"安装成功",Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(context, "root权限获取失败,尝试普通安装", Toast.LENGTH_SHORT).show();
                                   install(context,apkPath);
                               }
                      }
                       @Override
                        public void onError(Throwable e) {

                       }

                       @Override
                      public void onComplete() {

                       }
                });

    }
}

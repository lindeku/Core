package com.example.administrator.core.download;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/8/14 0014.
 */

@SuppressWarnings("DefaultFileTemplate")
public class DownloadConnection implements ServiceConnection {
    private DownloadBinder  binder=null;
    private  Disposable mDisposable=null;
    private IDownloadCondition mIDownloadCondition=null;
    public DownloadConnection(){}
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        binder= (DownloadBinder) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        binder=null;
    }

    public DownloadBinder getBinder() {
        return binder;
    }
    public void startDownload(String url,String apkName){
        long id = binder.startDownload(url, apkName);
        startCheckProgress(id);
    }

    private void startCheckProgress(final long id) {
        Observable
                .interval(100,200, TimeUnit.MICROSECONDS, Schedulers.io())
                .filter(new AppendOnlyLinkedArrayList.NonThrowingPredicate<Long>() {
                    @Override
                    public boolean test(Long aLong) {
                        return getBinder() != null;
                    }
                })
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(Long aLong) throws Exception {
                        return getBinder().getProgress(id);
                    }
                })
                .takeUntil(new AppendOnlyLinkedArrayList.NonThrowingPredicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) {
                        return integer >= 100;
                    }
                })
                .distinct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Integer value) {
                         if(mIDownloadCondition!=null){
                            mIDownloadCondition.onNext(value);
                         }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(mIDownloadCondition!=null){
                            mIDownloadCondition.onError(e);
                        }
                    }

                    @Override
                    public void onComplete() {
                        if(mIDownloadCondition!=null){
                            mIDownloadCondition.onComplete();
                        }
                    }
                });
    }
    public void setOnDownloadCondition(IDownloadCondition d){
        mIDownloadCondition=d;
    }
    public void dispose(){
        if(mDisposable!=null){
            mDisposable.dispose();
        }

    }
}

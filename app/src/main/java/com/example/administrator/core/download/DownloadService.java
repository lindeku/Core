package com.example.administrator.core.download;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.LongSparseArray;

/**
 * Created by Administrator on 2018/8/11 0011.
 */

@SuppressWarnings("DefaultFileTemplate")
public class DownloadService extends Service {
    private DownloadManager mDownloadManager=null;
    private LongSparseArray<String> mApkPaths;
    private DownloadBinder binder=null;
    private DownloadFinishReceiver mReceiver=new DownloadFinishReceiver();
    @Override
    public void onCreate() {
        super.onCreate();
        mDownloadManager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        mApkPaths = new LongSparseArray<>();
        binder=new DownloadBinder(this,mDownloadManager,mApkPaths,mReceiver);
        registerReceiver(mReceiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}

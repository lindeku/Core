package com.example.administrator.core.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.LongSparseArray;

/**
 * Created by Administrator on 2018/8/14 0014.
 * 下载完成的广播接收者
 */

public class DownloadFinishReceiver extends BroadcastReceiver {
    private Context mContext=null;
    private LongSparseArray<String> mApkPaths=null;
    private boolean mIsRoot=false;


    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setApkPaths(LongSparseArray<String> mApkPaths) {
        this.mApkPaths = mApkPaths;
    }

    public void setIsRoot(boolean mIsRoot) {
        this.mIsRoot = mIsRoot;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        String apkPath = mApkPaths.get(completeDownloadId);
        if (!apkPath.isEmpty()){
            SystemManager.setPermission(apkPath);//提升读写权限,否则可能出现解析异常
            InstallUtil.install(mContext,apkPath,mIsRoot);
        }
    }
}

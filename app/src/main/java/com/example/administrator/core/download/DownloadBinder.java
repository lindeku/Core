package com.example.administrator.core.download;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.util.Log;
import android.util.LongSparseArray;

import java.io.File;

/**
 * Created by Administrator on 2018/8/14 0014.
 */

public class DownloadBinder extends Binder {
    private Context mContext=null;
    private DownloadManager mDownloadManager=null;
    private LongSparseArray<String> mApkPaths=null;
    private DownloadFinishReceiver mDownloadFinishReceiver=null;
    public DownloadBinder(Context context,DownloadManager downloadManager,LongSparseArray<String> apkPath,DownloadFinishReceiver downloadFinishReceiver){
        mContext=context;
        mDownloadManager=downloadManager;
        mApkPaths=apkPath;
        mDownloadFinishReceiver=downloadFinishReceiver;
        mDownloadFinishReceiver.setContext(context);
    }
    public long startDownload(String  apkUrl,String apkName){
        IOUtils.clearApk(mContext,apkName);
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(apkUrl));
        File file=new File(mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),apkName);
        request.setDestinationUri(Uri.fromFile(file));
        request.setTitle("正在更新");
        long enqueue = mDownloadManager.enqueue(request);
        Log.e("DownloadBinder", file.getAbsolutePath());
        mApkPaths.put(enqueue,file.getAbsolutePath());
        mDownloadFinishReceiver.setApkPaths(mApkPaths);
        return enqueue;
    }
    public void setInstallMode(boolean isRoot){
        mDownloadFinishReceiver.setIsRoot(isRoot);
    }

    public int getProgress(long downloadId){
        DownloadManager.Query query=new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor=null;
        int progress=0;
        try {
            cursor=mDownloadManager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                //当前的下载量
                int downloadSoFar = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                //文件总大小
                int totalBytes = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                progress = (int) (downloadSoFar * 1.0f / totalBytes * 100);
            }
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return progress;
    }
}

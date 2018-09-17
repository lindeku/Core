package com.example.administrator.core.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/8/11 0011.
 */

@SuppressWarnings("DefaultFileTemplate")
public class DownloadUtils {
    private DownloadManager downloadManager=null;
    private Context mContext=null;
    private Long downloadID;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkStatus();
        }

    };
    public DownloadUtils(Context context){
        this.mContext=context;
    }

    public void downloadAPK(String url,String name){
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
        request.setAllowedOverMetered(true);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle("新版本Apk");
        request.setDescription("Apk Downloading");
        request.setVisibleInDownloadsUi(true);
        //下载路径
        request.setDestinationInExternalPublicDir(String.valueOf(mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)), name);

        downloadManager= (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadID= downloadManager.enqueue(request);
        mContext.registerReceiver(receiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private void checkStatus(){
        DownloadManager.Query query=new DownloadManager.Query();
        query.setFilterById(downloadID);
        Cursor c=downloadManager.query(query);
        if(c.moveToFirst()){
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status){
                //下载暂停
                case DownloadManager.STATUS_PAUSED:
                    break;
                //下载延迟
                case DownloadManager.STATUS_PENDING:
                    break;
                //正在下载
                case DownloadManager.STATUS_RUNNING:
                    break;
                //下载完成
                case DownloadManager.STATUS_SUCCESSFUL:
                    //下载完成安装APK
                    installAPK();
                    break;
                //下载失败
                case DownloadManager.STATUS_FAILED:
                    /*c.get*/
                    Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    }

    private void installAPK() {
        //获取下载文件的Uri
        Uri downloadFileUri = downloadManager.getUriForDownloadedFile(downloadID);
        if (downloadFileUri != null) {
            Intent intent= new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
            mContext.unregisterReceiver(receiver);
        }
    }

}

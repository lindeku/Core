package com.example.administrator.core.net.download;

import android.os.AsyncTask;

import com.example.administrator.core.net.RestCreator;
import com.example.administrator.core.net.RestService;
import com.example.administrator.core.net.callback.IError;
import com.example.administrator.core.net.callback.IFailure;
import com.example.administrator.core.net.callback.IRequest;
import com.example.administrator.core.net.callback.ISuccess;
import com.example.administrator.core.ui.loader.LatteLoader;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 林颖 on 2018/5/17.
 *
 */

public class DownloadHandler {
    private final String URL;
    private static final WeakHashMap<String,Object> PARAMS= RestCreator.getParams();
    private final String DOWNLOAD_DIN;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILUER;
    private final IRequest REQUEST;
    private final boolean IS_START_USIN_LOADER;
    private final RestService REST_SERVICE;
    private DownloadHandler(){
        this.URL=null;
        this.DOWNLOAD_DIN=null;
        this.EXTENSION=null;
        this.NAME=null;
        this.SUCCESS=null;
        this.ERROR=null;
        this.FAILUER=null;
        this.REQUEST=null;
        this.IS_START_USIN_LOADER=false;
        this.REST_SERVICE=null;

    }
    public DownloadHandler(String url, String downloadDin,
                           String extension, String name,
                           ISuccess success, IError error,
                           IFailure failure, IRequest iRequest,
                           boolean is_start_usin_loader,
                           RestService restService){
        this.URL=url;
        this.DOWNLOAD_DIN=downloadDin;
        this.EXTENSION=extension;
        this.NAME=name;
        this.SUCCESS=success;
        this.ERROR=error;
        this.FAILUER=failure;
        this.REQUEST=iRequest;
        this.IS_START_USIN_LOADER=is_start_usin_loader;
        this.REST_SERVICE=restService;
    }
    public final void handleDownload(){
        if(REQUEST!=null){
            REQUEST.onRequestStart();
        }
        if(REST_SERVICE!=null) {
            REST_SERVICE.download(URL, PARAMS).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        final ResponseBody responseBody = response.body();
                        final SaveFileTask task = new SaveFileTask(SUCCESS);
                        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIN, EXTENSION, NAME, responseBody);
                        if (task.isCancelled()) {
                            if (REQUEST != null) {
                                REQUEST.onRequestEnd();
                            }
                        }
                    } else {
                        if (ERROR != null) {
                            ERROR.onError(response.code(), response.message());
                        }
                    }
                    if (IS_START_USIN_LOADER) {
                        LatteLoader.stopLoading();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (FAILUER != null) {
                        FAILUER.onFailuer();
                    }
                    if (REQUEST != null) {
                        REQUEST.onRequestEnd();
                    }
                    if (IS_START_USIN_LOADER) {
                        LatteLoader.stopLoading();
                    }
                }
            });
        }
    }
}

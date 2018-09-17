package com.example.administrator.core.net.callback;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ot.www.ot_core.ui.loader.LatteLoader;
import com.wang.avi.Indicator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 林颖 on 2018/5/16.
 *
 */

public class RestCallback implements Callback<String> {
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILUER;
    private final IRequest REQUEST;
    private final Indicator LOADER_STYLE;
    private final Context CONTEXT;
    private final boolean mIsStatrUsngLoader;

    public RestCallback(ISuccess SUCCESS,
                        IError ERROR,
                        IFailure FAILUER,
                        IRequest REQUEST,
                        Indicator loader_style,
                        Context context,
                        boolean mIsStatrUsngLoader) {
        this.SUCCESS = SUCCESS;
        this.ERROR = ERROR;
        this.FAILUER = FAILUER;
        this.REQUEST = REQUEST;
        this.LOADER_STYLE = loader_style;
        this.CONTEXT=context;
        this.mIsStatrUsngLoader=mIsStatrUsngLoader;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        //是否请求成功
        if(response.isSuccessful()){
            //call是否在执行
            if(call.isExecuted()){
                if(SUCCESS!=null){
                    SUCCESS.onSuccess(response.body());
                }
            }
        }else {
            if(ERROR!=null){
                try {
                    final JSONObject err= JSON.parseObject(response.errorBody().string());
                    final String mseeage=err.getString("message");
                    final int errorCode=err.getInteger("errorCode");
                    ERROR.onError(errorCode,mseeage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(REQUEST!= null){
            REQUEST.onRequestEnd();
        }
        if(mIsStatrUsngLoader){
                LatteLoader.stopLoading();
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if(call.isCanceled()){
            Log.e("已经取消请求了","ok");
        }else {
            if (FAILUER != null) {
                FAILUER.onFailuer();
            }
            if (REQUEST != null) {
                REQUEST.onRequestEnd();
            }
            if (mIsStatrUsngLoader) {
                LatteLoader.stopLoading();
            }
        }
    }
}

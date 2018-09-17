package com.example.administrator.core.net.interceptors;

import android.support.annotation.RawRes;

import com.example.administrator.core.util.file.FileUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by 林颖 on 2018/5/18.
 *
 */

public class DebugInterceptor extends BaseInterceptor {
    private String mDebugUrl=null;
    private int dubugRawId=0;

    private DebugInterceptor(){};
    public DebugInterceptor(String mUrl, int dubugRawId) {
        this.mDebugUrl = mUrl;
        this.dubugRawId = dubugRawId;
    }
    private Response getResponse(Chain chain, String json){
        return new Response.Builder()
                .code(200)
                .addHeader("Context-Type","application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"),json))
                .message("ok")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();
    }
    private Response debugResponse(Chain chain, @RawRes int rawId){
        final String json= FileUtil.getRawFile(rawId);
        return getResponse(chain,json);
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        final String URL=chain.request().url().toString();
        if(URL.contains(mDebugUrl)){
           return   debugResponse(chain,dubugRawId);
        }
        return chain.proceed(chain.request());
    }
}

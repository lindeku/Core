package com.example.administrator.core.net;

import android.content.Context;

import com.example.administrator.core.net.callback.IError;
import com.example.administrator.core.net.callback.IFailure;
import com.example.administrator.core.net.callback.IRequest;
import com.example.administrator.core.net.callback.ISuccess;
import com.example.administrator.core.net.callback.IWithdraw;
import com.example.administrator.core.net.callback.RestCallback;
import com.example.administrator.core.net.download.DownloadHandler;
import com.example.administrator.core.ui.loader.LatteLoader;
import com.wang.avi.Indicator;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by 林颖 on 2018/5/15.
 *
 */

@SuppressWarnings("WeakerAccess")
public class RestClient {
    private final String URL;
    private static final WeakHashMap<String,Object> PARAMS=RestCreator.getParams();
    private final String DOWNLOAD_DIN;
    private final String EXTENSION;
    private final String NAME;
    private final RequestBody BODY;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final Indicator LOADER_STYLE;
    private final Context CONTEXT;
    private final IFailure FAILUER;
    private final IRequest REQUEST;
    private final File FILE;
    private final boolean IS_START_USIN_LOADER;
    private Call<String> call=null;
    private IWithdraw WITHDARAW;

    private RestClient(){
        FILE = null;
        IS_START_USIN_LOADER = false;
        ERROR = null;
        SUCCESS = null;
        URL = null;
        BODY = null;
        FAILUER = null;
        REQUEST = null;
        LOADER_STYLE = null;
        CONTEXT=null;
        DOWNLOAD_DIN=null;
        EXTENSION=null;
        NAME=null;
    }
    @SuppressWarnings("AccessStaticViaInstance")
    RestClient(String url,
               WeakHashMap<String,Object> params,
               RequestBody body,
               ISuccess success,
               IError error,
               IFailure failuer,
               IRequest request,
               Indicator style,
                Context contetx,
                boolean mIsStartUsingLoader,
                File file,
               String downloadDir,
               String extension,
               String name) {
        this.URL = url;
        this.PARAMS.putAll(params);
        this.BODY=body;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILUER = failuer;
        this.REQUEST = request;
        this.LOADER_STYLE = style;
        this.CONTEXT=contetx;
        this.IS_START_USIN_LOADER=mIsStartUsingLoader;
        this.FILE=file;
        this.DOWNLOAD_DIN=downloadDir;
        this.EXTENSION=extension;
        this.NAME=name;
    }
    private void request(HttpMethod method){
        if(REQUEST!= null){
            REQUEST.onRequestStart();
        }
        if(IS_START_USIN_LOADER){
            if(LOADER_STYLE!=null){
                LatteLoader.showLoading(CONTEXT,LOADER_STYLE);
            }else {
                LatteLoader.showLoading(CONTEXT);
            }
        }
        final RestService restService=RestCreator.getRestServicr();
        if(method.equals(HttpMethod.DOWNLOAD)&&method==HttpMethod.DOWNLOAD){
            downloadOperate(restService);
        }else {
            switch(method){
                case GET:
                    call=restService.get(URL,PARAMS);
                    break;
                case POST:
                    call=restService.post(URL,PARAMS);
                    break;
                case POST_RAW:
                    call=restService.postRaw(URL,BODY);
                    break;
                case PUT_RAW:
                    call=restService.putRaw(URL,BODY);
                    break;
                case PUT:
                    call=restService.put(URL,PARAMS);
                    break;
                case DELETE:
                    call=restService.delete(URL,PARAMS);
                    break;
                case UPLOAD:
                    final RequestBody requestBody=
                            RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                    final MultipartBody.Part body=
                            MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                    call=restService.upload(URL,body);
                    break;
                default:
                    break;
            }
            if(call!=null){
                call.enqueue(getCallback());
            }

        }

    }
    public void withdaraw(IWithdraw withdraw){
        if(call!=null) {
            withdraw.withdrawCall(call);
        }
    }
    @SuppressWarnings("WeakerAccess")
    public final RestCallback getCallback(){
        return  new RestCallback(SUCCESS,ERROR,FAILUER,REQUEST,LOADER_STYLE,CONTEXT,IS_START_USIN_LOADER);
    }
    public final void get(){
        request(HttpMethod.GET);
    }
    public final void post(){
        if(BODY==null){
            request(HttpMethod.POST);
        }else {
            if(PARAMS!=null){
                throw new RuntimeException("post参数不能为空");
            }
            request(HttpMethod.POST_RAW);
        }
    }
    public final void delete(){
        request(HttpMethod.DELETE);
    }
    public final void put(){
        if(BODY==null){
            request(HttpMethod.PUT);
        }else{
            if(PARAMS!=null){
                throw new RuntimeException("put参数不能为空");
            }
            request(HttpMethod.PUT_RAW);
        }

    }
    public final void download(){
        request(HttpMethod.DOWNLOAD);
    }
    @SuppressWarnings("FinalPrivateMethod")
    private final void downloadOperate(RestService service){
        new DownloadHandler(URL,DOWNLOAD_DIN,EXTENSION,NAME,SUCCESS,ERROR,FAILUER,REQUEST,IS_START_USIN_LOADER,service).handleDownload();
    }
    public static RestClintBuilder Builder(){
        return new RestClintBuilder();
    }

}

package com.example.administrator.core.net;

import android.content.Context;

import com.example.administrator.core.net.callback.IError;
import com.example.administrator.core.net.callback.IFailure;
import com.example.administrator.core.net.callback.IRequest;
import com.example.administrator.core.net.callback.ISuccess;
import com.wang.avi.Indicator;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by 林颖 on 2018/5/16.
 *
 */

 public class RestClintBuilder {
    private  String mUrl=null;
    private static WeakHashMap<String,Object> mParams=RestCreator.getParams();
    private ISuccess mISuccess=null;
    private IError mIError=null;
    private IFailure mIFailuer=null;
    private IRequest mIRequest=null;
    private RequestBody mBody=null;
    private Indicator mLoaderStyle=null;
    private  Context mContext=null;
    private boolean mIsStartUsingLoader=false;
    private File mFile=null;
    private String mDownloadDin=null;
    private String mExtension=null;
    private String mName=null;
    public RestClintBuilder(){}

    /**
     *
     * @param url 请求的URL
     * @return this
     */
    public final RestClintBuilder url(String url){
        this.mUrl=url;
        return this;
    }

    /**
     * 请求参数
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("JavaDoc")
    public final RestClintBuilder params(String key, Object value){
        mParams.put(key,value);
        return this;
    }
    public final RestClintBuilder params(WeakHashMap<String,Object> params){
        mParams.putAll(params);
        return this;
    }

    /**
     * 上传文件
     * @param mFile 上传的文件
     * @return this
     */
    public final RestClintBuilder file(File mFile){
        this.mFile=mFile;
        return this;
    }
    public final RestClintBuilder file(String file){
        this .mFile=new File(file);
        /*boolean a=*//*true*//**//*false*/
        return this;
    }

    /**
     * 设置文件存放的目录
     * @param dir 下载文件后存放的目录
     * @return this
     */
    public final  RestClintBuilder dir(String dir){
        this.mDownloadDin=dir;
        return this;
    }

    /**
     * 设置文件的后缀名
     * @param extension 文件后缀名
     * @return this
     */
    public final RestClintBuilder extensino(String extension){
        this.mExtension=extension;
        return this;
    }

    /**
     * @param name 文件名
     * @return this
     */
    public final RestClintBuilder name(String name){
        this.mName=name;
        return this;
    }
    /**
     * 是否启用Loader
     * 默认false
     * @param mIsStartUsingLoader is True 必须调用loader(),如果只传Context则使用默认的Loader
     * @return this
     */
    public final RestClintBuilder statrLoader(boolean mIsStartUsingLoader){
        this.mIsStartUsingLoader=mIsStartUsingLoader;
        return this;
    }

    /**
     * 设置Loader样式
     * 使用默认Loader
     * @param context 上下文
     * @return this
     */
    public final RestClintBuilder loader(Context context){
        this.mContext=context;
        return this;
    }

    /**
     * 设置Loader样式
     * @param context 上下文
     * @param style Loader样式
     * @return this
     */
    public final RestClintBuilder loader(Context context,Indicator style){
        this.mContext=context;
        this.mLoaderStyle=style;
        return this;
    }
    public final RestClintBuilder raw(String raw){
        this.mBody=RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),raw);
        return this;
    }

    /**
     * @param iSuccess 请求成功着回调
     * @return this
     */
    public final RestClintBuilder success(ISuccess iSuccess){
        this.mISuccess=iSuccess;
        return this;
    }

    /**
     * @param iError code码不为200 回调
     * @return this
     */
    public final RestClintBuilder error(IError iError){
        this.mIError=iError;
        return this;
    }

    /**
     * @param iFailure 无法连接到服务器回调
     * @return this
     */
    public final RestClintBuilder failure(IFailure iFailure){
        this.mIFailuer=iFailure;
        return this;
    }

    /**
     * @param iRequest 请求开始或结束回调
     * @return this
     */
    public final RestClintBuilder request(IRequest iRequest){
        this.mIRequest=iRequest;
        return this;
    }

    public final RestClient build(){
        if(mIsStartUsingLoader){
            if(mContext==null){
                throw  new RuntimeException("已启用Loader必须设置Context,调用相关方法 loader()");
            }
        }
        return  new RestClient(mUrl,
                mParams,
                mBody,
                mISuccess
                ,mIError,
                mIFailuer,
                mIRequest,
                mLoaderStyle,
                mContext,
                mIsStartUsingLoader,
                mFile,
                mDownloadDin,
                mExtension,
                mName);
    }

}

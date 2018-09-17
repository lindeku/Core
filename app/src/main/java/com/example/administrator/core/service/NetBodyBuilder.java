package com.example.administrator.core.service;

import com.example.administrator.core.net.web.IBaseCache;

import java.util.WeakHashMap;

public class NetBodyBuilder {
    private  WeakHashMap<String,String> mParameter=null;
    private  String mModuleName=null;
    private  IBaseCache mIBaseCache=null;
    private  int mAnalyzeCount=0;
    private  int mSuccess=0;
    private  int mFail=0;

    /**
     * 添加请求参数
     */
    public NetBodyBuilder addParameter(String key,String value){
        if(mParameter==null){
            mParameter=new WeakHashMap<>();
        }
        mParameter.put(key,value);
        return this;
    }

    /**
     * 设置当前 请求 成功数码
     */
    public NetBodyBuilder setSuccess(int success){
        this.mSuccess=success;
        return this;
    }

    /**
     * 设置当前 请求 失败数码
     */
    public NetBodyBuilder setFail(int fail){
        this.mFail=fail;
        return this;
    }

    /**
     * 设置解析数
     */
    public NetBodyBuilder setAndlyzeCount(int andlyzeCount){
        this.mAnalyzeCount=andlyzeCount;
        return this;
    }

    /**
     * 数据返回接口
     */
    public NetBodyBuilder writeCache(IBaseCache iBaseCache){
        this.mIBaseCache=iBaseCache;
        return this;
    }

    /**
     * 设置模块名
     */
    public NetBodyBuilder setModuleName(String name){
        this.mModuleName=name;
        return this;
    }
    public NetBody build(){
        return new NetBody(mParameter,mModuleName,mIBaseCache,mAnalyzeCount,mSuccess,mFail);
    }
}

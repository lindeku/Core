package com.example.administrator.core.service;

import java.util.ArrayList;
import java.util.List;

public class RequestBodyBuilder {
    private List<NetBody> mNets=null;
    private int mSuccess=0;
    private int mFail=0;
    private NetBody mNet=null;
    private void inspectObject(){
        if(mNets==null){
            mNets=new ArrayList<>();
        }
    }

    /**
     *
     *  单请求用setNet, 多请求用addNet,如果两者都涉及，先请求set的，再请求add
     */
    public RequestBodyBuilder setNet(NetBody net) {
        mNet=net;
        return this;
    }
    public RequestBodyBuilder addNet(NetBody net) {
        inspectObject();
        mNets.add(net);
        return this;
    }
    public RequestBodyBuilder setNetAll(List<NetBody> mNets) {
        this.mNets = mNets;
        return this;
    }

    /**
     * 设置本次所有请求 成功 数码
     */
    public RequestBodyBuilder setSuccess(int success) {
        this.mSuccess = success;
        return this;
    }
    /**
     * 设置本次所有请求 失败 数码
     */
    public RequestBodyBuilder setFail(int mFail) {
        this.mFail = mFail;
        return this;
    }
    public RequestBody build(){
       return new RequestBody(mNets,mNet,mSuccess,mFail);
    }
}

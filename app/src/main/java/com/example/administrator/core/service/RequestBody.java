package com.example.administrator.core.service;

import java.util.List;


public class RequestBody {
    private final int mSuccess;
    private final int mFail;
    private final List<NetBody> mNetBodys;
    private final NetBody mNet;


    RequestBody(List<NetBody> netBodys,NetBody netBody ,int success, int fail) {
        this.mSuccess = success;
        this.mFail = fail;
        this.mNetBodys = netBodys;
        mNet=netBody;
    }

    public int getSuccess() {
        return mSuccess;
    }

    public int getFail() {
        return mFail;
    }
    public NetBody getNetBody(){
        return mNet;
    }
    public NetBody getFirstNetBody(){
        return mNetBodys.get(0);
    }

    public List<NetBody> getNetBodys() {
        return mNetBodys;
    }

}

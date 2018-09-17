package com.example.administrator.core.mvp.model;

import com.example.administrator.core.mvp.Callback;

import java.util.WeakHashMap;

/**
 * Created by Administrator on 2018/7/14 0014.
 */

public abstract class BaseModel<T> {

    //数据请求参数
    protected WeakHashMap<String,Object> mParams;

    // 添加Callback并执行数据请求
    public abstract void execute(Callback<T> callback);


    /*// 执行Get网络请求，此类看需求由自己选择写与不写
    protected void requestGetAPI(String url,Callback<T> callback){
        //这里写具体的网络请求
    }*/
    /*
    // 执行Post网络请求，此类看需求由自己选择写与不写
    protected void requestPostAPI(String url, Map params,Callback<T> callback){
        //这里写具体的网络请求
    }*/

}

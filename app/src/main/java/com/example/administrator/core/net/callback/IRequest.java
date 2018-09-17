package com.example.administrator.core.net.callback;

/**
 * Created by 林颖 on 2018/5/15.
 */

public interface IRequest {
    /**
     * 请求开始回调
     */
    void onRequestStart();

    /**
     * 请求结束回调
     */
    void onRequestEnd();
}

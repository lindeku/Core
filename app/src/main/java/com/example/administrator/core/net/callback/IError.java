package com.example.administrator.core.net.callback;

/**
 * Created by 林颖 on 2018/5/15.
 */

public interface IError {
    /**
     * 请求异常回调
     * @param code code码
     * @param msg 错误信息
     */
    void onError(int code, String msg);
}

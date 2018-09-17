package com.example.administrator.core.mvp.preenler;

import com.example.administrator.core.mvp.view.IBaseView;


/**
 * Created by Administrator on 2018/7/14 0014.
 */

public class BasePresenter <V extends IBaseView> {
    private V mBaseView;
    /**
     *  绑定View
     */


    public void attachView(V  mvpView) {
        this.mBaseView= mvpView;
    }


    public <V>V getView(){
        return (V) mBaseView;
    }

    /**
     * 断开view
     */
    public void detachView() {
        this.mBaseView= null;
    }

    /**
     * 是否与View建立连接
     */
    public boolean isViewAttached(){
        return mBaseView != null;
    }


    /**
     *
     * 获取网络数据
     */
    public void getData(String params){
        mBaseView.hideLoading();
    }


}

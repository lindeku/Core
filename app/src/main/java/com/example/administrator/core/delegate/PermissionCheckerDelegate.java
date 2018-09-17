package com.example.administrator.core.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.administrator.core.mvp.preenler.BasePresenter;
import com.example.administrator.core.mvp.view.IBaseView;

/**
 * Created by Administrator on 2018/7/13 0013.
 */

public abstract  class PermissionCheckerDelegate extends BaseDelegate implements IBaseView {
    protected BasePresenter<PermissionCheckerDelegate> presenter=null;
    protected  <T extends BasePresenter>T writePresenter(){
        return null;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=writePresenter();
        if(presenter!=null){
            presenter.attachView(this);
        }


    }

    protected void uncultivated(){
        Toast.makeText(getContext(),"该模块待开发",Toast.LENGTH_SHORT).show();
    }
   /* public void showFailureMessage(String msg){

    }*/

    @Override
    public void showLoading() {
        checkActivityAttached();
    }

    @Override
    public void hideLoading() {
        checkActivityAttached();
    }

    @Override
    public void showData(String data) {
        checkActivityAttached();
    }

    @Override
    public void showFailureMessage(String msg) {
        checkActivityAttached();
        Toast.makeText(getContext(),msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showErrorMessage() {
        checkActivityAttached();
    }

    public void checkActivityAttached() {
        if (getActivity() == null) {
            throw new NullPointerException("Activity以销毁");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter!=null){
            if(!presenter.isViewAttached()){
                presenter.detachView();
            }
        }

    }
}

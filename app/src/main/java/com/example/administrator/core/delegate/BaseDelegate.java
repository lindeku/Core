package com.example.administrator.core.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.core.activity.ProxyActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by Administrator on 2018/7/13 0013.
 */

public abstract class BaseDelegate extends SwipeBackFragment {
    protected Unbinder mUnkinder =null;
    protected ViewGroup mContainer=null;
    protected ViewGroup getContainer() {
        return mContainer;
    }
    protected  abstract Object setLayout();
    public abstract void onBindView(@Nullable Bundle savedInstanceState, View rootView);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContainer=container;
        View    rootView=null;

        if(setLayout() instanceof Integer){
            rootView=inflater.inflate((Integer) setLayout(),container,false);
        }else if(setLayout() instanceof View){
            rootView= (View) setLayout();
        }else {
            throw new ClassCastException("只能是View或R资源");
        }

        if(rootView!=null){
            mUnkinder = ButterKnife.bind(this,rootView);
            onBindView(savedInstanceState,rootView);
        }
        return rootView;
    }

    public ProxyActivity getProxyActivity(){
        return (ProxyActivity) getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mUnkinder!=null){
            mUnkinder.unbind();
        }
    }

}

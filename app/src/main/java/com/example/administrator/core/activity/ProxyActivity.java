package com.example.administrator.core.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.example.administrator.core.R;
import com.example.administrator.core.delegate.PermissionCheckerDelegate;


import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Administrator on 2018/7/13 0013.
 */

public abstract class ProxyActivity extends SupportActivity {
    public abstract PermissionCheckerDelegate setRootDelegate();
    public abstract void getRootLayout(ContentFrameLayout view);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initContainer(savedInstanceState);
    }

    private void initContainer(Bundle savedInstanceState) {
        @SuppressLint("RestrictedApi")
        final ContentFrameLayout container=new ContentFrameLayout(this);
        container.setId(R.id.delegat_container);
        container.setFitsSystemWindows(false);
        getRootLayout(container);
        setContentView(container);
        if(savedInstanceState==null){

            loadRootFragment(R.id.delegat_container, setRootDelegate());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //垃圾回收机制
        System.gc();
        System.runFinalization();
    }
}

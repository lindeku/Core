package com.example.administrator.core.ui.loader.launche;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;


/**
 * Created by 林颖 on 2018/5/19.
 * 轮播图回调类
 */

public class LauncherHolderCreator implements CBViewHolderCreator<LauncherHolder> {
    @Override
    public LauncherHolder createHolder() {
        return new LauncherHolder();
    }
}

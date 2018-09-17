package com.example.administrator.core.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.administrator.core.app.Configure;


/**
 * Created by 林颖 on 2018/5/16.
 *
 */

@SuppressWarnings("FinalStaticMethod")
public class DimenUtill {
    public  static final int getScreenWidth(){
        final Resources resources = Configure.getContext().getResources();
        final DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public  static final int getScreenHeight(){
        final Resources resources = Configure.getContext().getResources();
        final DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

}

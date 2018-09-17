package com.example.administrator.core.util.layout;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by 林颖 on 2018/5/19.
 *
 */

public class InterfaceUtil {
    //全屏
    public static void fullScreen(AppCompatActivity activity){
        if(Build.VERSION.SDK_INT>=21) {
            View mDecorView = activity.getWindow().getDecorView();
            mDecorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            statusBar(activity);
        }
    }

    //透明状态栏
    public static void statusBar(AppCompatActivity activity){
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
    public static void sediment(AppCompatActivity activity){
        if(Build.VERSION.SDK_INT>=21){
            View decorView=activity.getWindow().getDecorView();
                        //全屏
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar=activity.getSupportActionBar();
        if(actionBar!=null) {
            actionBar.hide();

        }
    }
}

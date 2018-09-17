package com.example.administrator.core.ui.loader;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.core.R;
import com.example.administrator.core.util.dimen.DimenUtill;
import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;
import com.wang.avi.indicators.BallSpinFadeLoaderIndicator;

import java.util.ArrayList;


/**
 * Created by 林颖 on 2018/5/16.
 *
 */

public class LatteLoader {

    private static final int LOADER_SIZE_SCALE=8;
    private static final int LOADER_OFFSET_SCALE=10;
    private static final Indicator DEFAULT_LOADER=new BallSpinFadeLoaderIndicator();
    private static final ArrayList<AppCompatDialog>  LOADERS=new ArrayList<>();
    private static DialogInterface.OnKeyListener KEY_LISTENER=new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
            if (i == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };

    private static Handler handler=new Handler();
    public static void showLoading(Context context, Enum<LoaderStyle> styleName){
        showLoading(context,styleName.name());
    }
    public static void showLoading(Context context){
        showLoading(context,DEFAULT_LOADER);
    }
    public static void showLoading(Context context, String type){
        final AppCompatDialog dialog=new AppCompatDialog(context/*,R.style.d*/);
        final AVLoadingIndicatorView indicator=LoaderCreator.create(type,context);
         dialog.setContentView(indicator);
        int deviceWidth= DimenUtill.getScreenWidth();
        int deviceHeight= DimenUtill.getScreenHeight();
        final Window window=dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        if(window!=null){
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width=deviceWidth/LOADER_SIZE_SCALE;
            attributes.height=deviceHeight/LOADER_SIZE_SCALE;
            attributes.height=attributes.height+deviceHeight/LOADER_OFFSET_SCALE;
            attributes.gravity= Gravity.CENTER;
        }
        dialog.setOnKeyListener(KEY_LISTENER);
        LOADERS.add(dialog);
        handler.post(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });

    }


    public static void showLoading(Context context, Indicator type){
        final AppCompatDialog dialog=new AppCompatDialog(context , R.style.dialog);
        final AVLoadingIndicatorView indicator=LoaderCreator.create(type,context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(indicator);
        dialog.setCancelable(false);
        int deviceWidth= DimenUtill.getScreenWidth();
        int deviceHeight=DimenUtill.getScreenHeight();
        final Window window=dialog.getWindow();
        if(window!=null){
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width=deviceWidth/LOADER_SIZE_SCALE;
            attributes.height=deviceHeight/LOADER_SIZE_SCALE;
            /*attributes.type = ;*/
            attributes.height=attributes.height+deviceHeight/LOADER_OFFSET_SCALE;
            attributes.gravity= Gravity.CENTER;
        }
        dialog.setOnKeyListener(KEY_LISTENER);
        LOADERS.add(dialog);
        handler.post(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });
    }
    public static void stopLoading(){
        for(final AppCompatDialog dialog:LOADERS){
            if(dialog!=null){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.cancel();
                        }
                    },1000);
            }
        }
    }
    public static void stopLoading(int time){
        for(final AppCompatDialog dialog:LOADERS){
            if(dialog!=null){
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                    }
                },time);
            }
        }
    }



}

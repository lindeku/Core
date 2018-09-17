package com.example.administrator.core.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.core.util.dimen.DensityUtil;
import com.example.administrator.core.util.dimen.DimenUtill;

public abstract class BaseDialog extends Dialog {
    private int mLayout;
    public BaseDialog(@NonNull Context context,int layout) {
        super(context);
        mLayout=layout;
    }

    public BaseDialog(@NonNull Context context, int themeResId,int layout) {
        super(context, themeResId);
        mLayout=layout;
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener,int layout) {
        super(context, cancelable, cancelListener);
        mLayout=layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(mLayout, null);
        setContentView(view);

        Window window = getWindow();
        if(window!=null){

            WindowManager.LayoutParams layoutParams = window.getAttributes();
            DialogMeasure measure=new DialogMeasure();
            measure= onDraw(view,measure);

            if(measure.getType()==DialogMeasure.DP){
                layoutParams.width= DensityUtil.dip2px(getContext(), (float) measure.getWidth());
                layoutParams.height=DensityUtil.dip2px(getContext(), (float) measure.getHeight());
            }else if(measure.getType()==DialogMeasure.DX){
                int deviceWidth= DimenUtill.getScreenWidth();
                int deviceHeight=DimenUtill.getScreenHeight();
                layoutParams.width= (int)(deviceWidth/measure.getWidth());
                layoutParams.height= (int) (deviceHeight/measure.getHeight());
            }

            window.setAttributes(layoutParams);
        }
    }
    protected  abstract DialogMeasure onDraw (View view,DialogMeasure measure);
}

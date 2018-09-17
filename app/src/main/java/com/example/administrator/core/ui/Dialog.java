package com.example.administrator.core.ui;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Administrator on 2018/7/25 0025.
 */

@SuppressWarnings("DefaultFileTemplate")
public abstract class Dialog {
    private Activity context;
    private  AlertDialog dialog;
    public Dialog(Activity activity){
        this.context=activity;
    }
    public void init(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialog=builder.setView(getView(context.getLayoutInflater())).create();
    }
    public void show(){
        dialog.show();
        setDialog(dialog);
    }
    protected  abstract void setDialog(AlertDialog alertDialog);
    protected  abstract  View getView(LayoutInflater layoutInflater);

    protected Activity getContext() {
        return context;
    }
}

package com.example.administrator.core.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/7/15 0015.
 */

@SuppressWarnings("DefaultFileTemplate")
public class ViewHolder {
    private SparseArray<View> map;
    private int positton;
    private  View mConverView;
    public int getPositton(){
        return positton;
    }
    private Context context;

    public Context getContext() {
        return context;
    }

    private ViewHolder(Context context, ViewGroup viewGroup, int positton, int layout){
        this.context=context;
        map=new SparseArray<View>();
        this.positton=positton;
        mConverView= LayoutInflater.from(context).inflate(layout,viewGroup,false);
        mConverView.setTag(this);
    }
    public static ViewHolder get(Context context,View view, ViewGroup viewGroup,int positton,int layout){
        if(view==null){
            return new ViewHolder(context,viewGroup,positton,layout);
        }else {

            return (ViewHolder) view.getTag();
        }
    }
    public <T extends View>T getView(int viewId){
        View view=map.get(viewId);
        if(view==null){
            view=mConverView.findViewById(viewId);
            map.put(viewId,view);
        }
        return (T) view;
    }

    public View getmConverView() {
        return mConverView;
    }
}

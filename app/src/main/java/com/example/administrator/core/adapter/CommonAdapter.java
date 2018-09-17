package com.example.administrator.core.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/15 0015.
 */

@SuppressWarnings("DefaultFileTemplate")
public abstract class CommonAdapter<T> extends BaseAdapter {
    protected List<T> mlist;
    protected int layout;
    protected Context context;
    public CommonAdapter(Context context, List<T> list,int layout){
        this.mlist=list;
        this.context=context;
        this.layout=layout;
    }
    public void setData(List<T> list){
        this.mlist=list;
        notifyDataSetChanged();
    }
    public void clear(){
        mlist.clear();
        notifyDataSetChanged();
    }

    @SuppressWarnings("unchecked")
    public void setDeviceList(ArrayList<T> list) {
        if (list != null) {
            mlist = (List<T>) list.clone();
            notifyDataSetChanged();
        }
    }

    public List<T> getDatas(){
        return mlist;
    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public T getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=ViewHolder.get(context,view,viewGroup,i,layout);
        convert(holder,getItem(i));

        return holder.getmConverView();
    }
    protected abstract void convert(ViewHolder holder,T t);

    public void addData(T t){
        mlist.add(t);
        notifyDataSetChanged();
    }

    public void reconsitutionData(List<T> list){
        mlist=list;
        notifyDataSetChanged();
    }
}

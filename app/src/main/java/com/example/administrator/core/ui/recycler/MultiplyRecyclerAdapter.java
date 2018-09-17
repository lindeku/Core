package com.example.administrator.core.ui.recycler;

import android.support.v7.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/7/15 0015.
 */

public abstract class MultiplyRecyclerAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntry,MultipleViewHolder>
implements BaseQuickAdapter.SpanSizeLookup{

    protected MultiplyRecyclerAdapter(List<MultipleItemEntry> data) {
        super(data);
        //设置不同的item布局
        setDifferentLayout();
        //设置宽度监听
        setSpanSizeLookup(this);
        //打开的时候动画效果
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }


    protected abstract void setDifferentLayout();
    

    

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getFiled(MultipleFleids.ITEM_TYPE);
    }
}

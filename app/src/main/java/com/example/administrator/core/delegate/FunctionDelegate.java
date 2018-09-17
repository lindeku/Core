package com.example.administrator.core.delegate;

import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.administrator.core.R;

import org.w3c.dom.Text;

/**
 * Created by Administrator on  2018/7/13
 *      带TopBar的Delegate
 */

@SuppressWarnings("DefaultFileTemplate")
public abstract class FunctionDelegate extends PermissionCheckerDelegate {


    @Override
    public Object setLayout() {
        View rootView=LayoutInflater.from(getContext()).inflate(R.layout.context_topbar_layout,getContainer(),false);
        RelativeLayout toolbar=rootView.findViewById(R.id.toolbar);
        Button returnImg = rootView.findViewById(R.id.returnl);
        TextView title=rootView.findViewById(R.id.common_title_tv);
        Button menuImg=rootView.findViewById(R.id.common_menu);
        TextView menuText=rootView.findViewById(R.id.biaozhu);

        TopBarBinder topBarBinder=new TopBarBinder(menuImg,menuText,title,returnImg,toolbar);
        setTopBar(topBarBinder);
        LinearLayoutCompat linearLayoutCompat=rootView.findViewById(R.id.root_layout);
        if(setItemLayout() instanceof  View){
            linearLayoutCompat.addView(linearLayoutCompat);
        }else if(setItemLayout() instanceof Integer){
            View inflate = LayoutInflater.from(getContext()).inflate((Integer) setItemLayout(), linearLayoutCompat, false);
            linearLayoutCompat.addView(inflate);
        }
        return rootView;
    }



    protected  abstract void  setTopBar(TopBarBinder topBarBinder);
    public abstract Object setItemLayout();


}

package com.example.administrator.core.delegate;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TopBarBinder {
    private Button mButton;
    private TextView mTextView;
    private TextView mTitle;
    private Button mReturn;
    private RelativeLayout mToolbar;
    public TopBarBinder(Button button,TextView textView,TextView title,Button rEturn,RelativeLayout toolbar){
        this.mButton=button;
        this.mTextView=textView;
        this.mTitle=title;
        this.mReturn=rEturn;
        mToolbar=toolbar;
        initView();
    }
    public TopBarBinder setTitle(CharSequence title){
        mTitle.setText(title);
        return this;
    }
    public RelativeLayout getToolbar(){
        return mToolbar;
    }
    public TopBarBinder setMenuTitle(CharSequence menu){
        isShow(mTextView);

        mTextView.setText(menu);

        return this;
    }
    public TopBarBinder setMenuImg(Drawable resources){
        isShow(mButton);
        mButton.setBackground(resources);
        return this;
    }
    public TopBarBinder setReturnImg(Drawable resources){
        isShow(mReturn);
        mReturn.setBackground(resources);
        return this;
    }
    public TopBarBinder setReturnClickListener(View.OnClickListener i){
        isShow(mReturn);
        mReturn.setOnClickListener(i);
        return this;
    }
    public TopBarBinder setMenuClickListener(View.OnClickListener i){
        if(mTextView.getVisibility()==View.GONE && mButton.getVisibility()==View.GONE){
            throw new NullPointerException("必须先设置菜单样式");
        }else if(mTextView.getVisibility()==View.VISIBLE && mButton.getVisibility()==View.GONE){
            mTextView.setOnClickListener(i);
        }else if(mTextView.getVisibility()==View.GONE && mButton.getVisibility()==View.VISIBLE){
            mButton.setOnClickListener(i);
        }else {
            mTextView.setOnClickListener(i);
            mButton.setOnClickListener(i);
        }
        return this;
    }

    private void isShow(View view){
        if(view.getVisibility()==View.GONE){
            view.setVisibility(View.VISIBLE);
        }
    }
    private void initView(){
        mButton.setVisibility(View.GONE);
        mTextView.setVisibility(View.GONE);
        mReturn.setVisibility(View.GONE);
    }

}

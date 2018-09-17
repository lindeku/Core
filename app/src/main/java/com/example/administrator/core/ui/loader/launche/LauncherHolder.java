package com.example.administrator.core.ui.loader.launche;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 * Created by 林颖 on 2018/5/19.
 */

public class LauncherHolder implements Holder<Integer> {
    private ImageView mImage=null;
    @Override
    public View createView(Context context) {
        mImage=new ImageView(context);
        return mImage;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        mImage.setBackgroundResource(data);
    }
}

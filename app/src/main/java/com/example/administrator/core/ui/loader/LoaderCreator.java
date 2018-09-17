package com.example.administrator.core.ui.loader;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.HashMap;

/**
 * Created by 林颖 on 2018/5/16.
 *
 */

public class LoaderCreator {
    private static final HashMap<String,Indicator> LOADING_MAP=new HashMap<>();
    static AVLoadingIndicatorView create(String type, Context context){
        final AVLoadingIndicatorView avLoadingIndicatorView=new AVLoadingIndicatorView(context);
        if(LOADING_MAP.get(type)==null){
            final Indicator indicator=getIndicator(type);
            LOADING_MAP.put(type,indicator);
        }
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;
    }
    static AVLoadingIndicatorView create(Indicator indicator,Context context){
        final AVLoadingIndicatorView avLoadingIndicatorView=new AVLoadingIndicatorView(context);
        if(LOADING_MAP.get(indicator.getClass().getName())==null){
            LOADING_MAP.put(indicator.getClass().getName(),indicator);
        }
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(indicator.getClass().getName()));
        return avLoadingIndicatorView;
    }
    @SuppressWarnings("ConstantConditions")
    private static Indicator getIndicator(String name) {
        if (name == null && name.isEmpty()) {
            return null;
        }
        final StringBuilder builder = new StringBuilder();
        if (name.contains(".")) {
            final String dafaultPackageName = AVLoadingIndicatorView.class.getName();
            builder.append(dafaultPackageName)
                    .append(".");
        }
        builder.append(name);
        try {
            final Class<?> drawableClass = Class.forName(builder.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

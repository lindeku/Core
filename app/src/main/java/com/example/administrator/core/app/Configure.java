package com.example.administrator.core.app;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/7/13 0013.
 */

public class Configure {
    public static ConfiguresBuilder init(Context context){
        return  ConfiguresBuilder.getInstance().withContext(context);
    }
    public static Context getContext(){
        return (Context) getConfigurations().get(ConfiguresType.APPLICATION_CONTEXT);
    }

    public static HashMap<Object,Object> getConfigurations(){
        return ConfiguresBuilder.getConfigures();
    }

    public static ConfiguresBuilder getConfiguresBuilder(){
        return ConfiguresBuilder.getInstance();
    }
}

package com.example.administrator.core.net.interceptors;

import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/**
 * Created by 林颖 on 2018/5/18.
 */

public abstract class BaseInterceptor implements Interceptor {
    protected LinkedHashMap<String,String> getUrlParameters(Chain chain){
        final HttpUrl url=chain.request().url();
        final LinkedHashMap<String,String> linked=new LinkedHashMap<>();
        int quantity=url.querySize();
        for(int i=0;i<quantity;i++){
            linked.put(url.queryParameterName(i),url.queryParameterValue(i));
        }
        return linked;
    }
    protected String getUrlParameters(Chain chain,String key){
        return chain.request().url().queryParameter(key);
    }

    protected LinkedHashMap<String,String> getBodyParameters(Chain chain){
        final FormBody form= (FormBody) chain.request().body();
        final LinkedHashMap<String,String> linked=new LinkedHashMap<>();
        int quantiye=form.size();
        for(int i=0;i<quantiye;i++){
            linked.put(form.encodedName(i),form.encodedValue(i));
        }
        return linked;
    }
}

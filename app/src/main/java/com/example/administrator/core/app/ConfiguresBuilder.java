package com.example.administrator.core.app;

import android.content.Context;

import com.example.administrator.core.util.cache.FoundDisk;
import com.example.administrator.core.util.storage.LattePreference;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by Administrator on 2018/7/13 0013.
 */

@SuppressWarnings("DefaultFileTemplate")
public class ConfiguresBuilder {
    private static final HashMap<Object,Object> CONFIGURES =new HashMap<>();
    private static final ArrayList<Interceptor> INTERCEPTORS=new ArrayList<>();
    private ConfiguresBuilder(){}
    private static class  Holder{
        private static final ConfiguresBuilder CONFIGURES_BUILDER=new ConfiguresBuilder();
    }
    public static ConfiguresBuilder getInstance(){
        return Holder.CONFIGURES_BUILDER;
    }
    static final HashMap<Object, Object> getConfigures(){
        return CONFIGURES;
    }
    /**
     * 完成配置
     */
    public final void configure(){
        CONFIGURES.put(ConfiguresType.CONFIG_READY, true);
    }

    public final ConfiguresBuilder withContext(Context context){
        CONFIGURES.put(ConfiguresType.APPLICATION_CONTEXT,context);
        return this;
    }
    /**
     *
     * @param host 设置运行时  IP地址或域名
     */
    public final ConfiguresBuilder withMoveApiHost(String host){
        CONFIGURES.put(ConfiguresType.BASE_URL,host);
        return this;
    }

    /**
     *
     * @param host 设置存储缓存中  IP地址或域名
     */
    public final ConfiguresBuilder withMemoryApiHost(String host){
        LattePreference.addCustomAppProfile(ConfiguresType.BASE_URL.name(),host);
        CONFIGURES.put(ConfiguresType.BASE_URL,host);
        return this;
    }
    /**
     *
     * @param id 移动端识别ID
     */
    public final ConfiguresBuilder withCharacteristicID(String id){
        CONFIGURES.put(ConfiguresType.CHARACTERISTIC_ID,id);
        return  this;
    }

    /**
     *
     * @param spaceName webService命名空间
     */
    public final ConfiguresBuilder withNamespace(String spaceName){
        CONFIGURES.put(ConfiguresType.NAMESPACE,spaceName);
        return this;
    }

    public final ConfiguresBuilder with(Object key,Object v){
        CONFIGURES.put(key,v);
        return this;
    }

    /**
     *
     * @param uniqueName 缓存文件名
     */
    public final ConfiguresBuilder withDisk(Context context, String uniqueName){
        CONFIGURES.put(ConfiguresType.DISK, FoundDisk.openDisk(context,uniqueName));
        return this;
    }

    /**
     *
     * @return  获取全部拦截器
     */
    @SuppressWarnings("AccessStaticViaInstance")
    public final ArrayList<Interceptor>  getInterceptors(){
        return this.INTERCEPTORS;
    }
    public final ConfiguresBuilder withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        CONFIGURES.put(ConfiguresType.INTERCEPTORS,INTERCEPTORS);
        return this;
    }
    /**
     *  获取配置项
     */
    public <T>T getConfig(Object key){
        checkConfiguration();
        return (T)CONFIGURES.get(key);
    }

    private static void checkConfiguration(){
        boolean isReady= (boolean) CONFIGURES.get(ConfiguresType.CONFIG_READY);
        if(!isReady){
            throw new RuntimeException("配置项未完成，请调用configure()");
        }
    }


}

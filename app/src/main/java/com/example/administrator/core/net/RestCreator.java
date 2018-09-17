package com.example.administrator.core.net;

import com.example.administrator.core.app.Configure;
import com.example.administrator.core.app.ConfiguresType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 林颖 on 2018/5/15.
 *
 */

public class RestCreator {
    /**
     * Api容器
     */
    private static final HashMap<Object,Object> API=new HashMap<>();


    /**
     * 参数容器
     */
    private static final class ParamsHolder{
        private static final WeakHashMap<String,Object> PARAMS=new WeakHashMap<>();
    }
    public static WeakHashMap<String,Object> getParams(){
        return ParamsHolder.PARAMS;
    }

    /**
     * 构建全局Retrofit客户端
     */
    private static final class RetrofitHolder{
        private static final String BASE_URL= Configure.getConfiguresBuilder().getConfig(ConfiguresType.BASE_URL);
        private static final Retrofit RETROFIT_CLIENT=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        /**
         *构建OkHttp
         */
        private static final class OkHttpHolder{
            private static final int TIME_OUT=60;
            private static final OkHttpClient.Builder BUILDER=new OkHttpClient.Builder();
            private static final ArrayList<Interceptor> INTERCEPTORS=Configure.getConfiguresBuilder().getInterceptors();
            private static OkHttpClient.Builder addInterceptor(){
                if(INTERCEPTORS!=null&&!INTERCEPTORS.isEmpty()){
                    for(Interceptor i:INTERCEPTORS){
                        BUILDER.addInterceptor(i);
                    }
                }
                return  BUILDER;
            }
            private static final OkHttpClient OK_HTTP_CLIENT=addInterceptor()
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .build();
        }
    }

    /**
     * Service接口
     */
    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE= RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    public static <T>T setRestApi(Object object,Object mclass){
        API.put(object,RetrofitHolder.RETROFIT_CLIENT.create(mclass.getClass()));
        return (T) RetrofitHolder.RETROFIT_CLIENT.create(mclass.getClass());
    }
    public static HashMap<Object,Object> getApis(){
        return API;
    }
    public static RestService getRestServicr(){
        return RestServiceHolder.REST_SERVICE;
    }

}

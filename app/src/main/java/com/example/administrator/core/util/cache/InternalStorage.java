package com.example.administrator.core.util.cache;

import android.support.v4.util.LruCache;

/**
 * Created by 林颖 on 2018/6/17.
 */

public class InternalStorage {
    private LruCache<Object,Object> mLruCache;

    private InternalStorage(){
        int maxSize= (int) (Runtime.getRuntime().maxMemory()/8);//获取最大的缓存大小
        if(mLruCache==null){
            mLruCache=new LruCache<Object,Object>(maxSize){
                @Override
                protected int sizeOf(Object key, Object value) {
                    return super.sizeOf(key, value);
                }
            };
        }
    }
    public LruCache<Object,Object> lruCache(){
        return mLruCache;
    }

    public static InternalStorage getInstance(){
        return InternalStorageHandler.INTERNAL_STORAGE;
    }

    private static class InternalStorageHandler{
        private static final InternalStorage INTERNAL_STORAGE=new InternalStorage();
    }
}

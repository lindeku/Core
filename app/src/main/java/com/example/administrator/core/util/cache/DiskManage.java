package com.example.administrator.core.util.cache;


import com.example.administrator.core.app.Configure;
import com.example.administrator.core.app.ConfiguresType;

/**
 * Created by 林颖 on 2018/6/17.
 */

public class DiskManage {
    private  static final InternalStorage INTERNAL_STORAGE=InternalStorage.getInstance();
    private  static final FoundDisk FOUUND_DISK = Configure.getConfiguresBuilder().getConfig(ConfiguresType.DISK);
    public static void put(String key,Object v){
        FOUUND_DISK.put(key,v);
    }
    public static Object get(String key){
        Object lru = INTERNAL_STORAGE.lruCache().get(key);
        if(lru==null){
            Object asObject = FOUUND_DISK.getAsObject(key);
            if(asObject!=null){
                INTERNAL_STORAGE.lruCache().put(key,asObject);
                return asObject;
            }
            return null;
        }
        return lru;
    }
    public static boolean remove(String key){
        return FOUUND_DISK.remove(key);
    }

}

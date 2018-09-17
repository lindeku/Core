package com.example.administrator.core.service;

import com.example.administrator.core.net.web.IBaseCache;

import java.util.WeakHashMap;

public class NetBody {
    private final WeakHashMap<String,String> PARAMETER;
    private final int ANALYZE_COUNT;
    private final String MODULE_NAME;
    private final int SUCCESS;
    private final int FAIL;
    private final IBaseCache IBASE_CACHE;
    private NetBody(){
         this.PARAMETER = null;
         this.ANALYZE_COUNT=0;
         this.SUCCESS=0;
         this.FAIL=0;
         this.MODULE_NAME=null;
         this.IBASE_CACHE=null;
     }
     NetBody(WeakHashMap<String, String> parameter,String moduleName,IBaseCache iBaseCache, int analyzeCount,int success,int fail ) {
        this.PARAMETER = parameter;
        this.ANALYZE_COUNT = analyzeCount;
        this.MODULE_NAME = moduleName;
        this.IBASE_CACHE=iBaseCache;
        this.SUCCESS=success;
        this.FAIL=fail;
    }
    public WeakHashMap<String, String> getParameter() {
        return PARAMETER;
    }
    public int getSuccess() {
        return SUCCESS;
    }
    public int getFail() {
        return FAIL;
    }
    public int getAnalyzeCount() {
        return ANALYZE_COUNT;
    }
    public IBaseCache getIBaseCache() {
        return IBASE_CACHE;
    }
    public String getModuleName() {
        return MODULE_NAME;
    }

}

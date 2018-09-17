package com.example.administrator.core.ui.recycler;

import java.util.LinkedHashMap;
import java.util.WeakHashMap;

/**
 * Created by Administrator on 2018/7/15 0015.
 */

public class MultipleEntityBuilder {
    private final static LinkedHashMap<Object,Object> FIELDS=new LinkedHashMap<>();
    public MultipleEntityBuilder(){
        FIELDS.clear();
    }
    public final MultipleEntityBuilder setItemType(int itemType){
        FIELDS.put(MultipleFleids.ITEM_TYPE,itemType);
        return this;
    }
    public final MultipleEntityBuilder setFiled(Object key,Object value){
        FIELDS.put(key, value);
        return this;
    }
    public final MultipleEntityBuilder setFileds(WeakHashMap<Object,Object> map){
        FIELDS.putAll(map);
        return this;
    }
    public final MultipleItemEntry build(){
        return new MultipleItemEntry(FIELDS);
    }
}

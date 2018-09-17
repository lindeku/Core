package com.example.administrator.core.ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2018/7/15 0015.
 */

@SuppressWarnings("DefaultFileTemplate")
public class MultipleItemEntry implements MultiItemEntity {
    private final ReferenceQueue<LinkedHashMap<Object,Object>> ITEM_QUENE=new ReferenceQueue<>();
    private final LinkedHashMap<Object,Object> MULTIPLE_FIELDS=new LinkedHashMap<>();

    private final SoftReference<LinkedHashMap<Object,Object>> FIELDS_REFERENCE=
            new SoftReference<LinkedHashMap<Object, Object>>(MULTIPLE_FIELDS,ITEM_QUENE);

    private MultipleItemEntry(){}
    MultipleItemEntry(LinkedHashMap<Object,Object> map){
        MULTIPLE_FIELDS.putAll(map);
    }
    public static MultipleEntityBuilder builder(){
        return new MultipleEntityBuilder();
    }

    public final LinkedHashMap<?,?> getFileds(){
        return FIELDS_REFERENCE.get();
    }
    @SuppressWarnings("unchecked")
    public final <T>T getFiled(Object key){
        return (T) FIELDS_REFERENCE.get().get(key);
    }

    public final MultipleItemEntry setFiled(Object key,Object value){
        FIELDS_REFERENCE.get().put(key, value);
        return this;
    }

    @Override
    public int getItemType() {
        return (int) FIELDS_REFERENCE.get().get(MultipleFleids.ITEM_TYPE);
    }
}

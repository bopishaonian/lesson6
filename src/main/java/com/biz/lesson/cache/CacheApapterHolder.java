package com.biz.lesson.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 缓存适配器持有者
 */
@Component
public class CacheApapterHolder{

    @Autowired(required = false)
    private List<CacheAdapter> adapters = null;

    public List<CacheAdapter> getAdapters() {
        return adapters;
    }

    public void setAdapters(List<CacheAdapter> adapters) {
        this.adapters = adapters;
    }
   

}

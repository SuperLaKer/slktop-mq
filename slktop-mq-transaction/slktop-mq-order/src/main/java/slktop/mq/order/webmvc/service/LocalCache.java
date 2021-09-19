package slktop.mq.order.webmvc.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @description: 缓存管理工具，采用LRU淘汰策略
 **/
@Component
public class LocalCache<T> {

    private Cache<String, T> localCache = null;

    @PostConstruct
    private void init() {
        localCache = CacheBuilder.newBuilder()
                //设置本地缓存容器的初始容量
                .initialCapacity(10)
                //设置本地缓存的最大容量
                .maximumSize(500)
                //设置写缓存后多少秒过期
                .expireAfterWrite(1, TimeUnit.SECONDS).build();
    }

    public void setLocalCache(String key, T object) {
        localCache.put(key, object);
    }

    public <T> T getCache(String key) {
        return (T) localCache.getIfPresent(key);
    }

    public void remove(String key) {
        localCache.invalidate(key);
    }

}

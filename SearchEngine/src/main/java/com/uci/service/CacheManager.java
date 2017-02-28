package com.uci.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by junm5 on 2/27/17.
 */
@Component
public class CacheManager {
    // CACHE_NAME在redis中会作为key的前缀
    private static final String CACHE_NAME = "document:";
    // 过期时间设为0，表示永不过期。
    private static final int EXPIRE_TIME = 0;

    @Autowired
    private StringRedisTemplate template;

    private RedisCache cache;

    @PostConstruct
    public void init() {
        cache = new RedisCache(CACHE_NAME, CACHE_NAME.getBytes(), template, EXPIRE_TIME);
    }

    // redis set <K,V>
    public void put(String key, Object obj) {
        cache.put(key, obj);
    }

    // redis get <K>
    public <T> T get(String key, Class<T> clas) {
        return cache.get(key) == null ? null :
                cache.get(key, clas);
    }
    // redis del <K>
    public void del(String key) {
        cache.evict(key);
    }
}
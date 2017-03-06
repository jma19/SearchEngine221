package com.uci.db;

import com.uci.constant.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static com.uci.constant.Table.ANCHOR;
import static com.uci.constant.Table.DOCUMENT;
import static com.uci.constant.Table.TERM;

/**
 * Created by junm5 on 2/27/17.
 */
@Service
public class DBHandler {

    private Map<Table, RedisCache> redisCacheMap = new HashMap<>();
    private static final int EXPIRE_TIME = 0;

    @Autowired
    private StringRedisTemplate template;

    @PostConstruct
    public void init() {
        redisCacheMap.put(DOCUMENT, new RedisCache(DOCUMENT.getName(), DOCUMENT.getName().getBytes(), template, EXPIRE_TIME));
        redisCacheMap.put(TERM, new RedisCache(TERM.getName(), TERM.getName().getBytes(), template, EXPIRE_TIME));
        redisCacheMap.put(ANCHOR, new RedisCache(ANCHOR.getName(), ANCHOR.getName().getBytes(), template, EXPIRE_TIME));
    }


    // redis set <K,V>
    public void put(Table table, String key, Object obj) {
        redisCacheMap.get(table).put(key, obj);
    }

    // redis get <K>
    public <T> T get(Table table, String key, Class<T> clas) {
        T t = redisCacheMap.get(table).get(key, clas);
        return t == null ? null : t;
    }

    public boolean containsKey(Table table, String key) {
        return redisCacheMap.get(table).get(key) != null;
    }

    public void clearAll(Table table) {
        redisCacheMap.get(table).clear();
    }
}

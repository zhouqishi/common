package com.stone.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yuanxiu
 * @date 2020/9/25
 *
 * lru 淘汰算法
 * 核心LinkedHashMap
 */
public class LruHashMap<K, V> extends LinkedHashMap<K, V> {

    private static final long serialVersionUID = 1L;
    private final int maxCacheSize;

    LruHashMap(int initialCapacity, int maxCacheSize) {
        super(initialCapacity, 0.75F, true);
        this.maxCacheSize = maxCacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > this.maxCacheSize;
    }

}

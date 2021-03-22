package com.lamvanhai.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheImpl<K, V> implements Cache<K, V> {

    // cache L1, L2, L3 (share memory), c
    private volatile Map<K, V> cache = new ConcurrentHashMap<>();

    @Override
    public void put(K k, V v) {
        cache.put(k, v);
    }

    @Override
    public V get(K k) {
        return cache.get(k);
    }

    public Map<K, V> getCache() {
        return cache;
    }
}

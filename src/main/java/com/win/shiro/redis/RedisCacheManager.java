/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.win.shiro.redis;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.win.shiro.redis.api.RedisManagerApi;

/**
 *
 * @author Administrator
 */
public class RedisCacheManager implements CacheManager{

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);
    // fast lookup by name map
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

    protected RedisManagerApi redisManager;

    public RedisCacheManager(RedisManagerApi redisManager) {
        this.redisManager = redisManager;
    }

    public RedisManagerApi getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManagerApi redisManager) {
        this.redisManager = redisManager;
    }

    /**
     * The Redis key prefix for caches
     */
    private String keyPrefix = "shiro_redis_cache:";

    /**
     * Returns the Redis session keys prefix.
     *
     * @return The prefix
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * Sets the Redis sessions key prefix.
     *
     * @param keyPrefix The prefix
     */
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("获取名称为: " + name + " 的RedisCache实例");
        Cache c = caches.get(name);

        if (c == null) {
            // create a new cache instance
            c = new RedisCache<K, V>(redisManager, keyPrefix);
            // add it to the cache collection
            caches.put(name, c);
        }
        return c;
    }


}

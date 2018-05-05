/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.win.shiro.redis.manager;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import com.win.shiro.redis.api.RedisManagerApi;
import org.springframework.util.SerializationUtils;

/**
 *
 * @author Administrator
 */
public class RedisClusterManager implements RedisManagerApi {

    private static Logger logger = LoggerFactory.getLogger(RedisClusterManager.class);

    
    protected JedisCluster jedisCluster;

    public RedisClusterManager(JedisCluster jedisCluster) {
         this.jedisCluster = jedisCluster;
    }

    /**
     * get value from redis
     *
     * @param key
     * @return
     */
    @Override
    public Object get(String key) {
        return SerializationUtils.deserialize(jedisCluster.get(getByteKey(key)));
    }

    
    private byte[] getByteKey(String key){
       return SerializationUtils.serialize(key);
    }


    /**
     * set
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public Object set(String key, Object value) {
         byte[] bkey=getByteKey(key);
        jedisCluster.set(bkey, SerializationUtils.serialize(value));
        return value;
    }

    /**
     * set
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    @Override
    public Object set(String key, Object value, int expire) {
        byte[] bkey=getByteKey(key);
        jedisCluster.set(bkey, SerializationUtils.serialize(value));
        jedisCluster.expire(bkey, expire);
        return value;
    }

    /**
     * set
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    @Override
    public String set(String key, String value, int expire) {
        jedisCluster.set(key, value);
        jedisCluster.expire(key, expire);
        return value;
    }

    /**
     * del
     *
     * @param key
     */
    @Override
    public void del(byte[] key) {
        jedisCluster.del(key);
    }

    /**
     * del
     *
     * @param key
     */
    @Override
    public void del(String key) {
        jedisCluster.del(key);
    }

    
     /**
     * keys
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        Set<String> keys = new HashSet<>();
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
        for (String k : clusterNodes.keySet()) {
            JedisPool jp = clusterNodes.get(k);
            Jedis connection = jp.getResource();
            try {
                connection.keys(getByteKey(pattern)).stream().forEach(p->keys.add((String)SerializationUtils.deserialize(p)));
            } catch (Exception e) {
                logger.error("Getting keys error: {}", e);
            } finally {
                logger.debug("Connection closed.");
                connection.close();//用完一定要close这个链接！！！  
            }
        }
        return keys;
    }
    
    /**
     * flush
     */
    @Override
    public void flushDB() {
        jedisCluster.flushDB();
    }

    /**
     * size
     */
    @Override
    public Long dbSize() {
        return jedisCluster.dbSize();
    }

}

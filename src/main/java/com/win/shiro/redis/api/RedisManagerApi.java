/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.win.shiro.redis.api;

import java.util.Set;

/**
 *
 * @author Administrator
 */
public interface RedisManagerApi {
    /**
     * get value from redis
     *
     * @param key
     * @return
     */
    public Object get(String key) ;

    /**
     * set
     *
     * @param key
     * @param value
     * @return
     */
    public Object set(String key, Object value) ;


    /**
     * set
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public Object set(String key, Object value, int expire);

    /**
     * set
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public String set(String key, String value, int expire);

    /**
     * del
     *
     * @param key
     */
    public void del(byte[] key);

    /**
     * del
     *
     * @param key
     */
    public void del(String key);

    /**
     * flush
     */
    public void flushDB();

    /**
     * size
     */
    public Long dbSize();
     /**
     * keys
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern);
}

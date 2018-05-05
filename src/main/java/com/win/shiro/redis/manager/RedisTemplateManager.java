/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.win.shiro.redis.manager;

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import com.win.shiro.redis.api.RedisManagerApi;

/**
 *
 * @author Administrator
 */
public class RedisTemplateManager implements RedisManagerApi {

    private static Logger logger = LoggerFactory.getLogger(RedisTemplateManager.class);

    private RedisTemplate redisTemplate;
    
    public RedisTemplateManager(RedisTemplate redisTemplate) {
        this.redisTemplate=redisTemplate;
    }

    @Override
    public Object get(String key) {
       return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Object set(String key, Object value) {
         redisTemplate.opsForValue().set(key,value);
        return  value;
    }

    @Override
    public Object set(String key, Object value, int expire) {
         redisTemplate.opsForValue().set(key,value,expire);
        return  value;
    }

    @Override
    public String set(String key, String value, int expire) {
         redisTemplate.opsForValue().set(key,value,expire);
        return  value;
    }

    @Override
    public void del(byte[] key) {
         redisTemplate.delete(key);
    }

    @Override
    public void del(String key) {
         redisTemplate.delete(key);
    }

    @Override
    public void flushDB() {
       
    }

    @Override
    public Long dbSize() {
        return  0L;
    }

    @Override
    public Set<String> keys(String pattern) {
      return redisTemplate.keys(pattern);
    }

}

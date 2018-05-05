/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.win.shiro.redis;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SerializationUtils;
import com.win.shiro.redis.api.RedisManagerApi;

/**
 *
 * @author Administrator
 */
public class RedisSessionDAO extends AbstractSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);
    /**
     * shiro-redis的session对象前缀
     */
    private RedisManagerApi redisManager;

    public RedisSessionDAO(RedisManagerApi redisManager) {
        this.redisManager = redisManager;
    }
    
    /**
     * The Redis key prefix for the sessions
     */
    private String keyPrefix = "shiro_session:";

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    /**
     * save session
     *
     * @param session
     * @throws UnknownSessionException
     */
    private void saveSession(Session session) throws UnknownSessionException {
        System.out.println("----saveSession---" + session.getId());
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
        this.redisManager.set(getKey(session.getId()), session, Integer.parseInt(session.getTimeout() + ""));

    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
        redisManager.del(this.getKey(session.getId()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<Session>();
        Set<String> keys = redisManager.keys(this.keyPrefix + "*");
        if(keys != null && keys.size()>0){
            for(String key:keys){
                Session s = (Session)redisManager.get(key);
                sessions.add(s);
            }
        }
        return sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            logger.error("session id is null");
            return null;
        }

        Session s = (Session) redisManager.get(this.getKey(sessionId));
        return s;
    }

    /**
     * 获得byte[]型的key
     *
     * @param sessionId
     * @return
     */
    private String getKey(Serializable sessionId) {
        return this.keyPrefix + sessionId.toString();
    }

    public RedisManagerApi getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManagerApi redisManager) {
        this.redisManager = redisManager;
    }

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
}

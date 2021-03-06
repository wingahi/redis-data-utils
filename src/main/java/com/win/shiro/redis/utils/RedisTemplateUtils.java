/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.win.shiro.redis.utils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.util.SafeEncoder;
import com.win.shiro.redis.utils.api.RedisUtilsApi;
import java.util.List;
import java.util.Map;
import java.util.Set;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.SortingParams;

/**
 *
 * @author Administrator
 */
public class RedisTemplateUtils implements RedisUtilsApi {

    private RedisTemplate redisTemplate;

    public RedisTemplateUtils(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 更改key
     *
     * @param String oldkey
     * @param String newkey
     * @ 状态码
     *
     */
    public String rename(String oldkey, String newkey) {
        return rename(SafeEncoder.encode(oldkey),
                SafeEncoder.encode(newkey));
    }

    /**
     * 更改key,仅当新key不存在时才执行
     *
     * @param String oldkey
     * @param String newkey
     * @ 状态码
     *
     */
    public long renamenx(String oldkey, String newkey) {
        redisTemplate.rename(oldkey, newkey);
        return 0;
    }

    /**
     * 更改key
     *
     * @param String oldkey
     * @param String newkey
     * @ 状态码
     *
     */
    public String rename(Object oldkey, Object newkey) {
        redisTemplate.rename(oldkey, newkey);
        return "1";
    }

    /**
     * 设置key的过期时间，以秒为单位
     *
     * @param String key
     * @param 时间 ,已秒为单位
     * @ 影响的记录数
     *
     */
    public long expired(String key, int seconds) {
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        return 1;
    }

    /**
     * 设置key的过期时间,它是距历元（即格林威治标准时间 1970 年 1 月 1 日的 00:00:00，格里高利历）的偏移量。
     *
     * @param String key
     * @param 时间 ,已秒为单位
     * @ 影响的记录数
     *
     */
    public long expireAt(String key, long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        redisTemplate.expireAt(key, calendar.getTime());
        return 1;
    }

    /**
     * 查询key的过期时间
     *
     * @param String key
     * @ 以秒为单位的时间表示
     *
     */
    public long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 取消对key过期时间的设置
     *
     * @param key
     * @ 影响的记录数
     *
     */
    public long persist(String key) {
        redisTemplate.persist(key);
        return 1;
    }

    /**
     * 删除keys对应的记录,可以是多个key
     *
     * @param String ... keys
     * @ 删除的记录数
     *
     */
    public long del(String... keys) {
        redisTemplate.delete(Arrays.asList(keys));
        return keys.length;
    }

 

    /**
     * 根据key获取记录
     *
     * @param Object key
     * @ 值
     *
     */
    public Object get(Object key) {
        //ShardedJedis jedisCluster = getShardedJedis();
        Object value = (Object) redisTemplate.opsForValue().get(key);
        return value;
    }

    /**
     * 添加记录,如果记录已存在将覆盖原有的value
     *
     * @param String key
     * @param String value
     * @ 状态码
     *
     */
    public String set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return "1";
    }

    

    /**
     * 删除keys对应的记录,可以是多个key
     *
     * @param String ... keys
     * @ 删除的记录数
     *
     */
    public long del(Object... keys) {
        redisTemplate.delete(Arrays.asList(keys));
        return keys.length;
    }

    /**
     * 判断key是否存在
     *
     * @param String key
     * @ boolean
     *
     */
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 对List,Set,SortSet进行排序,如果集合数据较大应避免使用这个方法
     *
     * @param String key
     * @ List<String> 集合的全部记录 *
     */
    public List<String> sort(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 对List,Set,SortSet进行排序或limit
     *
     * @param String key
     * @param SortingParams parame 定义排序类型或limit的起止位置.
     * @ List<String> 全部或部分记录 *
     */
    public List<String> sort(String key, SortingParams parame) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 返回指定key存储的类型
     *
     * @param String key
     * @ String string|list|set|zset|hash *
     */
    public String type(String key) {
        return redisTemplate.type(key).toString();
    }

    //*******************************************Sets*******************************************//
    /**
     * 向Set添加一条记录，如果member已存在返回0,否则返回1
     *
     * @param String key
     * @param String member
     * @ 操作码,0或1
     *
     */
    public long sadd(String key, String member) {
        return redisTemplate.opsForSet().add(key, member);

    }

    public long sadd(Object key, Object member) {
        return redisTemplate.opsForSet().add(key, member);

    }

    /**
     * 获取给定key中元素个数
     *
     * @param String key
     * @ 元素个数
     *
     */
    public long scard(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 返回从第一组和所有的给定集合之间的差异的成员
     *
     * @param String ... keys
     * @ 差异的成员集合
     *
     */
    public Set<String> sdiff(String... keys) {

        throw new UnsupportedOperationException("不支持该方法");
    }

    /**
     * 这个命令等于sdiff,但返回的不是结果集,而是将结果集存储在新的集合中，如果目标已存在，则覆盖。
     *
     * @param String newkey 新结果集的key
     * @param String ... keys 比较的集合
     * @ 新集合中的记录数 *
     */
    public long sdiffstore(String newkey, String... keys) {

        throw new UnsupportedOperationException("不支持该方法");
    }

    /**
     * 返回给定集合交集的成员,如果其中一个集合为不存在或为空，则返回空Set
     *
     * @param String ... keys
     * @ 交集成员的集合 *
     */
    public Set<String> sinter(String... keys) {
        throw new UnsupportedOperationException("不支持该方法");
    }

    /**
     * 这个命令等于sinter,但返回的不是结果集,而是将结果集存储在新的集合中，如果目标已存在，则覆盖。
     *
     * @param String newkey 新结果集的key
     * @param String ... keys 比较的集合
     * @ 新集合中的记录数 *
     */
    public long sinterstore(String newkey, String... keys) {

        throw new UnsupportedOperationException("不支持该方法");
    }

    /**
     * 确定一个给定的值是否存在
     *
     * @param String key
     * @param String member 要判断的值
     * @ 存在返回1，不存在返回0 *
     */
    public boolean sismember(String key, String member) {
        //ShardedJedis jedisCluster = getShardedJedis();
        return redisTemplate.opsForSet().isMember(key, member);
    }

    /**
     * 返回集合中的所有成员
     *
     * @param String key
     * @ 成员集合
     *
     */
    public Set<String> smembers(String key) {
        //ShardedJedis jedisCluster = getShardedJedis();

        Set<String> set = redisTemplate.opsForSet().members(key);

        return set;
    }

    public Set<Object> smembers(Object key) {
        //ShardedJedis jedisCluster = getShardedJedis();

        Set<Object> set = redisTemplate.opsForSet().members(key);

        return set;
    }

    /**
     * 将成员从源集合移出放入目标集合 <br/>
     * 如果源集合不存在或不包哈指定成员，不进行任何操作，返回0<br/>
     * 否则该成员从源集合上删除，并添加到目标集合，如果目标集合中成员已存在，则只在源集合进行删除
     *
     * @param String srckey 源集合
     * @param String dstkey 目标集合
     * @param String member 源集合中的成员
     * @ 状态码，1成功，0失败
     *
     */
    public long smove(String srckey, String dstkey, String member) {

        return redisTemplate.opsForSet().move(srckey, dstkey, member) ? 1 : 0;
    }

    /**
     * 从集合中删除成员
     *
     * @param String key
     * @ 被删除的成员
     *
     */
    public String spop(String key) {

        return (String) redisTemplate.opsForSet().pop(key);
    }

    /**
     * 从集合中删除指定成员
     *
     * @param String key
     * @param String member 要删除的成员
     * @ 状态码，成功返回1，成员不存在返回0
     *
     */
    public long srem(String key, String member) {

        long s = redisTemplate.opsForSet().remove(key, member);

        return s;
    }

    /**
     * 合并多个集合并返回合并后的结果，合并后的结果集合并不保存<br/>
     *
     * @param String ... keys
     * @ 合并后的结果集合
     * @see sunionstore
     *
     */
    public Set<String> sunion(String... keys) {

        throw new UnsupportedOperationException("不支持该方法");
    }

    /**
     * 合并多个集合并将合并后的结果集保存在指定的新集合中，如果新集合已经存在则覆盖
     *
     * @param String newkey 新集合的key
     * @param String ... keys 要合并的集合 *
     */
    public long sunionstore(String newkey, String... keys) {

        long s = redisTemplate.opsForSet().union(newkey, Arrays.asList(keys)).size();

        return s;
    }

    //*******************************************SortSet*******************************************//
    /**
     * 向集合中增加一条记录,如果这个值已存在，这个值对应的权重将被置为新的权重
     *
     * @param String key
     * @param double score 权重
     * @param String member 要加入的值，
     * @ 状态码 1成功，0已存在member的值
     *
     */
    public long zadd(String key, double score, String member) {

        long s = redisTemplate.opsForZSet().add(key, member, score) ? 1 : 0;

        return s;
    }

    public long zadd(String key, Map<String, Double> scoreMembers) {

        return redisTemplate.opsForZSet().add(key, scoreMembers.entrySet());
    }

    /**
     * 获取集合中元素的数量
     *
     * @param String key
     * @ 如果返回0则集合不存在
     *
     */
    public long zcard(String key) {
        //ShardedJedis jedisCluster = getShardedJedis();

        long len = redisTemplate.opsForZSet().zCard(key);

        return len;
    }

    /**
     * 获取指定权重区间内集合的数量
     *
     * @param String key
     * @param double min 最小排序位置
     * @param double max 最大排序位置
     *
     */
    public long zcount(String key, double min, double max) {
        //ShardedJedis jedisCluster = getShardedJedis();

        long len = redisTemplate.opsForZSet().count(key, min, max);

        return len;
    }

    /**
     * 获得set的长度
     *
     * @param key
     * @
     */
    public long zlength(String key) {
        long len = 0;
        Set<String> set = zrange(key, 0, -1);
        len = set.size();
        return len;
    }

    /**
     * 权重增加给定值，如果给定的member已存在
     *
     * @param String key
     * @param double score 要增的权重
     * @param String member 要插入的值
     * @ 增后的权重
     *
     */
    public double zincrby(String key, double score, String member) {

        double s = redisTemplate.opsForZSet().incrementScore(key, member, score);

        return s;
    }

    /**
     * 返回指定位置的集合元素,0为第一个元素，-1为最后一个元素
     *
     * @param String key
     * @param int start 开始位置(包含)
     * @param int end 结束位置(包含)
     * @ Set<String>
     *
     */
    public Set<String> zrange(String key, int start, int end) {
        //ShardedJedis jedisCluster = getShardedJedis();

        Set<String> set = redisTemplate.opsForZSet().range(key, start, end);

        return set;
    }

    /**
     * 返回指定权重区间的元素集合
     *
     * @param String key
     * @param double min 上限权重
     * @param double max 下限权重
     * @ Set<String>
     *
     */
    public Set<String> zrangeByScore(String key, double min, double max) {
        //ShardedJedis jedisCluster = getShardedJedis();

        Set<String> set = redisTemplate.opsForZSet().rangeByScore(key, min, max);

        return set;
    }

    /**
     * 获取指定值在集合中的位置，集合排序从低到高
     *
     * @see zrevrank
     * @param String key
     * @param String member
     * @ long 位置
     *
     */
    public long zrank(String key, String member) {
        //ShardedJedis jedisCluster = getShardedJedis();

        long index = redisTemplate.opsForZSet().rank(key, member);

        return index;
    }

    /**
     * 获取指定值在集合中的位置，集合排序从高到低
     *
     * @see zrank
     * @param String key
     * @param String member
     * @ long 位置
     *
     */
    public long zrevrank(String key, String member) {
        //ShardedJedis jedisCluster = getShardedJedis();

        long index = redisTemplate.opsForZSet().reverseRank(key, member);

        return index;
    }

    /**
     * 从集合中删除成员
     *
     * @param String key
     * @param String member
     * @ 返回1成功
     *
     */
    public long zrem(String key, String member) {

        long s = redisTemplate.opsForZSet().remove(key, member);

        return s;
    }

    /**
     * 删除
     *
     * @param key
     * @
     */
    public long zrem(String key) {

        long s = redisTemplate.opsForZSet().remove(key);

        return s;
    }

    /**
     * 删除给定位置区间的元素
     *
     * @param String key
     * @param int start 开始区间，从0开始(包含)
     * @param int end 结束区间,-1为最后一个元素(包含)
     * @ 删除的数量
     *
     */
    public long zremrangeByRank(String key, int start, int end) {

        long s = redisTemplate.opsForZSet().removeRange(key, start, end);

        return s;
    }

    /**
     * 删除给定权重区间的元素
     *
     * @param String key
     * @param double min 下限权重(包含)
     * @param double max 上限权重(包含)
     * @ 删除的数量
     *
     */
    public long zremrangeByScore(String key, double min, double max) {

        long s = redisTemplate.opsForZSet().removeRangeByScore(key, min, max);

        return s;
    }

    /**
     * 获取给定区间的元素，原始按照权重由高到低排序
     *
     * @param String key
     * @param int start
     * @param int end
     * @ Set<String>
     *
     */
    public Set<String> zrevrange(String key, int start, int end) {
        //ShardedJedis jedisCluster = getShardedJedis();

        Set<String> set = redisTemplate.opsForZSet().reverseRange(key, start, end);

        return set;
    }

    /**
     * 获取给定值在集合中的权重
     *
     * @param String key
     * @param memeber
     * @ double 权重
     *
     */
    public double zscore(String key, String memebr) {
        //ShardedJedis jedisCluster = getShardedJedis();

        Double score = redisTemplate.opsForZSet().score(key, memebr);

        if (score != null) {
            return score;
        }
        return 0;
    }

    //*******************************************Hash*******************************************//
    /**
     * 从hash中删除指定的存储
     *
     * @param String key
     * @param String fieid 存储的名字
     * @ 状态码，1成功，0失败
     *
     */
    public long hdel(String key, String fieid) {

        long s = redisTemplate.opsForHash().delete(key, fieid);

        return s;
    }

    public long hdel(String key) {

        long s = redisTemplate.opsForHash().delete(key);

        return s;
    }

    /**
     * 测试hash中指定的存储是否存在
     *
     * @param String key
     * @param String fieid 存储的名字
     * @ 1存在，0不存在
     *
     */
    public boolean hexists(String key, String fieid) {
        //ShardedJedis jedisCluster = getShardedJedis();

        boolean s = redisTemplate.opsForHash().hasKey(key, fieid);

        return s;
    }

    /**
     * 返回hash中指定存储位置的值
     *
     * @param String key
     * @param String fieid 存储的名字
     * @ 存储对应的值
     *
     */
    public String hget(String key, String fieid) {
        //ShardedJedis jedisCluster = getShardedJedis();

        String s = (String) redisTemplate.opsForHash().get(key, fieid);

        return s;
    }

    public Object hget(Object key, Object fieid) {
        //ShardedJedis jedisCluster = getShardedJedis();

        Object s = (Object) redisTemplate.opsForHash().get(key, fieid);

        return s;
    }

    /**
     * 以Map的形式返回hash中的存储和值
     *
     * @param String key
     * @ Map<Strinig,String>
     *
     */
    public Map<String, String> hgetAll(String key) {
        Map<String, String> map = redisTemplate.opsForHash().entries(key);
        return map;
    }

    /**
     * 添加一个对应关系
     *
     * @param String key
     * @param String fieid
     * @param String value
     * @ 状态码 1成功，0失败，fieid已存在将更新，也返回0 *
     */
    public long hset(String key, String fieid, String value) {

        redisTemplate.opsForHash().put(key, fieid, value);

        return 1;
    }

    public long hset(String key, String fieid, Object value) {

        redisTemplate.opsForHash().put(key.getBytes(), fieid.getBytes(), value);

        return 1;
    }

    /**
     * 添加对应关系，只有在fieid不存在时才执行
     *
     * @param String key
     * @param String fieid
     * @param String value
     * @ 状态码 1成功，0失败fieid已存 *
     */
    public long hsetnx(String key, String fieid, String value) {

        redisTemplate.opsForHash().put(key, fieid, value);

        return 1;
    }

    /**
     * 获取hash中value的集合
     *
     * @param String key
     * @ List<String>
     *
     */
    public List<String> hvals(String key) {
        //ShardedJedis jedisCluster = getShardedJedis();

        List<String> list = redisTemplate.opsForHash().values(key);

        return list;
    }

    /**
     * 在指定的存储位置加上指定的数字，存储位置的值必须可转为数字类型
     *
     * @param String key
     * @param String fieid 存储位置
     * @param String long value 要增加的值,可以是负数
     * @ 增加指定数字后，存储位置的值
     *
     */
    public long hincrby(String key, String fieid, long value) {

        long s = redisTemplate.opsForHash().increment(key, fieid, value);

        return s;
    }

    /**
     * 返回指定hash中的所有存储名字,类似Map中的keySet方法
     *
     * @param String key
     * @ Set<String> 存储名称的集合
     *
     */
    public Set<String> hkeys(String key) {
        //ShardedJedis jedisCluster = getShardedJedis();

        Set<String> set = redisTemplate.opsForHash().keys(key);

        return set;
    }

    /**
     * 获取hash中存储的个数，类似Map中size方法
     *
     * @param String key
     * @ long 存储的个数
     *
     */
    public long hlen(String key) {
        //ShardedJedis jedisCluster = getShardedJedis();

        long len = redisTemplate.opsForHash().size(key);

        return len;
    }

    /**
     * 根据多个key，获取对应的value，返回List,如果指定的key不存在,List对应位置为null
     *
     * @param String key
     * @param String ... fieids 存储位置
     * @ List<String>
     *
     */
    public List<String> hmget(String key, String... fieids) {
        //ShardedJedis jedisCluster = getShardedJedis();

        List<String> list = redisTemplate.opsForHash().multiGet(key, Arrays.asList(fieids));

        return list;
    }

    public List<Object> hmget(Object key, Object... fieids) {
        //ShardedJedis jedisCluster = getShardedJedis();

        List<Object> list = redisTemplate.opsForHash().multiGet(key, Arrays.asList(fieids));

        return list;
    }

    /**
     * 添加对应关系，如果对应关系已存在，则覆盖
     *
     * @param Strin key
     * @param Map
     * <String,String> 对应关系
     * @ 状态，成功返回OK
     *
     */
    public String hmset(String key, Map<String, String> map) {

        redisTemplate.opsForHash().putAll(key, map);

        return "1";
    }

    /**
     * 添加对应关系，如果对应关系已存在，则覆盖
     *
     * @param Strin key
     * @param Map
     * <String,String> 对应关系
     * @ 状态，成功返回OK
     *
     */
    public String hmset(Object key, Map<Object, Object> map) {

        redisTemplate.opsForHash().putAll(key, map);
        return "1";
    }

    /**
     * 添加有过期时间的记录
     *
     * @param String key
     * @param int seconds 过期时间，以秒为单位
     * @param String value
     * @ String 操作状态
     *
     */
    public String setEx(String key, int seconds, String value) {

        redisTemplate.opsForValue().set(key, value, seconds);

        return "1";
    }

    /**
     * 添加有过期时间的记录
     *
     * @param String key
     * @param int seconds 过期时间，以秒为单位
     * @param String value
     * @ String 操作状态
     *
     */
    public String setEx(Object key, int seconds, Object value) {

        redisTemplate.opsForValue().set(key, value, seconds);

        return "1";
    }

    /**
     * 添加一条记录，仅当给定的key不存在时才插入
     *
     * @param String key
     * @param String value
     * @ long 状态码，1插入成功
     *
     */
    public long setnx(String key, String value) {

        redisTemplate.opsForValue().setIfAbsent(key, value);
        return 1;
    }

    /**
     * 添加记录,如果记录已存在将覆盖原有的value
     *
     * @param Object key
     * @param Object value
     * @ 状态码
     *
     */
    public String set(Object key, Object value) {

        redisTemplate.opsForValue().set(key, value);

        return "1";
    }

    /**
     * 从指定位置开始插入数据，插入的数据会覆盖指定位置以后的数据<br/>
     * 例:String str1="123456789";<br/>
     * 对str1操作后setRange(key,4,0000)，str1="123400009";
     *
     * @param String key
     * @param long offset
     * @param String value
     * @ long value的长度
     *
     */
    public long setRange(String key, long offset, String value) {
        throw new UnsupportedOperationException();
    }

    /**
     * 在指定的key中追加value
     *
     * @param String key
     * @param String value
     * @ long 追加后value的长度 *
     */
    public long append(String key, String value) {

        long len = redisTemplate.opsForValue().append(key, value);

        return len;
    }

    /**
     * 将key对应的value减去指定的值，只有value可以转为数字时该方法才可用
     *
     * @param String key
     * @param long number 要减去的值
     * @ long 减指定值后的值
     *
     */
    public long decrBy(String key, long number) {
        throw new UnsupportedOperationException();
    }

    /**
     * <b>可以作为获取唯一id的方法</b><br/>
     * 将key对应的value加上指定的值，只有value可以转为数字时该方法才可用
     *
     * @param String key
     * @param long number 要减去的值
     * @ long 相加后的值
     *
     */
    public long incrBy(String key, long number) {

        long len = redisTemplate.opsForValue().increment(key, number);

        return len;
    }

    /**
     * 对指定key对应的value进行截取
     *
     * @param String key
     * @param long startOffset 开始位置(包含)
     * @param long endOffset 结束位置(包含)
     * @ String 截取的值
     *
     */
    public String getrange(String key, long startOffset, long endOffset) {
        //ShardedJedis jedisCluster = getShardedJedis();
        throw new UnsupportedOperationException();
    }

    /**
     * 获取并设置指定key对应的value<br/>
     * 如果key存在返回之前的value,否则返回null
     *
     * @param String key
     * @param String value
     * @ String 原始value或null
     *
     */
    public String getSet(String key, String value) {

        String str = (String)redisTemplate.opsForValue().getAndSet(key, value);

        return str;
    }

    /**
     * 批量获取记录,如果指定的key不存在返回List的对应位置将是null
     *
     * @param String keys
     * @ List<String> 值得集合
     *
     */
    public List<String> mget(String... keys) {

        List<String> str = redisTemplate.opsForValue().multiGet(Arrays.asList(keys));

        return str;
    }

    /**
     * 批量存储记录
     *
     * @param String keysvalues 例:keysvalues="key1","value1","key2","value2";
     * @ String 状态码
     *
     */
    public String mset(String... keysvalues) {

        throw new UnsupportedOperationException();
    }

    /**
     * 获取key对应的值的长度
     *
     * @param String key
     * @ value值得长度
     *
     */
    public long strlen(String key) {

        long len =  redisTemplate.opsForValue().size(key);

        return len;
    }

    //*******************************************Lists*******************************************//
    /**
     * List长度
     *
     * @param String key
     * @ 长度
     *
     */
    public long llen(String key) {
        return llen(SafeEncoder.encode(key));
    }

    /**
     * List长度
     *
     * @param Object key
     * @ 长度
     *
     */
    public long llen(Object key) {
        //ShardedJedis jedisCluster = getShardedJedis();

        long count =  redisTemplate.opsForList().size(key);

        return count;
    }

    /**
     * 覆盖操作,将覆盖List中指定位置的值
     *
     * @param Object key
     * @param int index 位置
     * @param Object value 值
     * @ 状态码
     *
     */
    public String lset(Object key, int index, Object value) {

        redisTemplate.opsForList().leftPush(key, index , value);
        return "1";
    }

    /**
     * 覆盖操作,将覆盖List中指定位置的值
     *
     * @param key
     * @param int index 位置
     * @param String value 值
     * @ 状态码
     *
     */
    public String lset(String key, int index, String value) {
        return lset(SafeEncoder.encode(key), index,
                SafeEncoder.encode(value));
    }

    /**
     * 在value的相对位置插入记录
     *
     * @param key
     * @param LIST_POSITION 前面插入或后面插入
     * @param String pivot 相对位置的内容
     * @param String value 插入的内容
     * @ 记录总数
     *
     */
    public long linsert(String key, BinaryClient.LIST_POSITION where, String pivot,
            String value) {
        return linsert(SafeEncoder.encode(key), where,
                SafeEncoder.encode(pivot), SafeEncoder.encode(value));
    }

    /**
     * 在指定位置插入记录
     *
     * @param String key
     * @param LIST_POSITION 前面插入或后面插入
     * @param Object pivot 相对位置的内容
     * @param Object value 插入的内容
     * @ 记录总数
     *
     */
    public long linsert(Object key, BinaryClient.LIST_POSITION where, Object pivot,
            Object value) {

        throw new UnsupportedOperationException();
    }

  

    /**
     * 获取List中指定位置的值
     *
     * @param Object key
     * @param int index 位置
     * @ 值 *
     */
    public Object lindex(Object key, int index) {
        //ShardedJedis jedisCluster = getShardedJedis();

        Object value = (Object)redisTemplate.opsForList().index(key, index);

        return value;
    }

   
    /**
     * 将List中的第一条记录移出List
     *
     * @param Object key
     * @ 移出的记录
     *
     */
    public Object lpop(Object key) {

        Object value = (Object)redisTemplate.opsForList().leftPop(key);

        return value;
    }

    /**
     * 将List中最后第一条记录移出List
     *
     * @param Object key
     * @ 移出的记录
     *
     */
    public String rpop(String key) {

        String value = (String)redisTemplate.opsForList().rightPop(key);

        return value;
    }

    /**
     * 向List尾部追加记录
     *
     * @param String key
     * @param String value
     * @ 记录总数
     *
     */
    public long lpush(String key, String value) {
        return lpush(SafeEncoder.encode(key), SafeEncoder.encode(value));
    }

    /**
     * 向List头部追加记录
     *
     * @param String key
     * @param String value
     * @ 记录总数
     *
     */
    public long rpush(String key, String value) {

        long count = redisTemplate.opsForList().rightPush(key, value);

        return count;
    }

    /**
     * 向List头部追加记录
     *
     * @param String key
     * @param String value
     * @ 记录总数
     *
     */
    public long rpush(Object key, Object value) {

        long count = redisTemplate.opsForList().rightPush(key, value);

        return count;
    }

    /**
     * 向List中追加记录
     *
     * @param Object key
     * @param Object value
     * @ 记录总数
     *
     */
    public long lpush(Object key, Object value) {

        long count = redisTemplate.opsForList().leftPush(key, value);

        return count;
    }

    /**
     * 获取指定范围的记录，可以做为分页使用
     *
     * @param String key
     * @param long start
     * @param long end
     * @ List
     *
     */
    public List<String> lrange(String key, long start, long end) {
        //ShardedJedis jedisCluster = getShardedJedis();

        List<String> list = redisTemplate.opsForList().range(key, start, end);

        return list;
    }

    /**
     * 获取指定范围的记录，可以做为分页使用
     *
     * @param Object key
     * @param int start
     * @param int end 如果为负数，则尾部开始计算
     * @ List
     *
     */
    public List<Object> lrange(Object key, int start, int end) {
        //ShardedJedis jedisCluster = getShardedJedis();

        List<Object> list = redisTemplate.opsForList().range(key, start, end);

        return list;
    }

    /**
     * 删除List中c条记录，被删除的记录值为value
     *
     * @param Object key
     * @param int c 要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
     * @param Object value 要匹配的值
     * @ 删除后的List中的记录数
     *
     */
    public long lrem(Object key, int c, Object value) {

        long count = redisTemplate.opsForList().remove(key, c, value);

        return count;
    }

    /**
     * 删除List中c条记录，被删除的记录值为value
     *
     * @param String key
     * @param int c 要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
     * @param String value 要匹配的值
     * @ 删除后的List中的记录数
     *
     */
    public long lrem(String key, int c, String value) {
        return lrem(SafeEncoder.encode(key), c, SafeEncoder.encode(value));
    }

    /**
     * 算是删除吧，只保留start与end之间的记录
     *
     * @param Object key
     * @param int start 记录的开始位置(0表示第一条记录)
     * @param int end 记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
     * @ 执行状态码
     *
     */
    public String ltrim(Object key, int start, int end) {

       redisTemplate.opsForList().trim(key, start, end);

        return "1";
    }

    /**
     * 算是删除吧，只保留start与end之间的记录
     *
     * @param String key
     * @param int start 记录的开始位置(0表示第一条记录)
     * @param int end 记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
     * @ 执行状态码
     *
     */
    public String ltrim(String key, int start, int end) {
        return ltrim(SafeEncoder.encode(key), start, end);
    }

    @Override
    public String rename(byte[] oldkey, byte[] newkey) {
        redisTemplate.rename(oldkey, newkey);
        return "1";
    }


    @Override
    public String set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        return "1";
    }


    @Override
    public Object lindex(String key, int index) {
        return redisTemplate.opsForList().index(key, index);
    }

    @Override
    public String lpop(String key) {
        redisTemplate.opsForList().leftPop(key);
        return  "1";
    }


  

}

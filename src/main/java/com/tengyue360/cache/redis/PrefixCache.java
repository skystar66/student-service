package com.tengyue360.cache.redis;

import com.tengyue360.cache.ICache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author xuliang
 * @date 2018/08/24 19:53.
 **/
@Component("prefixCache")
public class PrefixCache implements ICache {
    private final Logger logger = LoggerFactory.getLogger(PrefixCache.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Object readConfig(String key) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    @Override
    public void setConfig(String key, String value) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(key, value);
    }

    @Override
    public Object readConfigByGroup(String group, Object key) {
        HashOperations<String, Object, Object> operateions = redisTemplate.opsForHash();
        return operateions.get(group, key);
    }

    @Override
    public void setConfigByGroup(String group, String key, Object value) {
        HashOperations<String, Object, Object> operateions = redisTemplate.opsForHash();
        operateions.put(group, key, value);
    }

    @Override
    public boolean setLoseTime(String key, Long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    @Override
    public Long getCount(String key) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        return operations.increment(key, 1);
    }

    @Override
    public Long getCountByGroup(String group, String key) {
        HashOperations<String, Object, Object> operateions = redisTemplate.opsForHash();
        return operateions.increment(group, key, 1);
    }

    @Override
    public void batchDel(String... pattern) {
        for (String kp : pattern) {
            redisTemplate.delete(redisTemplate.keys("*" + kp + "*"));
        }
    }
}

package com.tengyue360.cache.redis;

import com.tengyue360.cache.ICountCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


import java.util.concurrent.TimeUnit;

/**
 * @author xuliang
 * @date 2018/08/24 19:53.
 **/
@Component("interfaceCountCache")
public class InterfaceCountCache implements ICountCache {

    private final Logger LOG = LoggerFactory.getLogger(InterfaceCountCache.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
	public synchronized boolean isRequestOutInterface(String key, Long outTime) {

        long count = redisTemplate.opsForValue().increment(key, 1);
        if (count == 1) {
            redisTemplate.expire(key, outTime, TimeUnit.SECONDS);
            return true;
        }
        return false;
	}
    @Override
    public boolean setFailure(String key) {
        return redisTemplate.expire(key, 0, TimeUnit.SECONDS);
    }

    @Override
    public String getValue(String key) {
        HashOperations<String,Object,Object> operateions = redisTemplate.opsForHash();
        if(operateions.get("student",key)==null){
            return "false";
        }
        return operateions.get("student",key).toString();
    }

    @Override
    public void setValue(Object key,Object value) {
        HashOperations<String,Object,Object> operateions = redisTemplate.opsForHash();
        operateions.put("student",key,value);
    }


}

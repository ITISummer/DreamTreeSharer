package com.ITIS.DreamTreeSharer.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisUtil
 * @Author LCX
 * @Date 2021 2021-05-07 2:02 p.m.
 * @Version 1.0
 **/
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * @param key
     * @param value
     * @param timeout 单位-秒
     */
    public void setKey(String key, String value, Integer timeout) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, value, timeout, TimeUnit.SECONDS);//5min 过期
    }

    public String getValue(String key) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }
}

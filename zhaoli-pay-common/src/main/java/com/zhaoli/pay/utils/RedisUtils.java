package com.zhaoli.pay.utils;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisUtils {
    private static StringRedisTemplate stringRedisTemplate
            = SpringUtil.getBean("stringRedisTemplate");


    /**
     * 存放string类型
     *
     * @param key     key
     * @param data    数据
     * @param timeout 超时间
     */
    public static void setString(String key, String data, Long timeout) {
        stringRedisTemplate.opsForValue().set(key, data);
        if (timeout != null) {
            stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 存放string类型
     *
     * @param key  key
     * @param data 数据
     */
    public static void setString(String key, Object data) {
        setString(key, String.valueOf(data), null);
    }

    /**
     * 根据key查询string类型
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        String value = stringRedisTemplate.opsForValue().get(key);
        return value;
    }

    /**
     * 根据对应的key删除key
     *
     * @param key
     */
    public static void delKey(String key) {
        stringRedisTemplate.delete(key);
    }


}
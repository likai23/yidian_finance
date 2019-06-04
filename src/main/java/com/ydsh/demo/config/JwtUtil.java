package com.ydsh.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Auth yaozhongjie
 * @Date 2019/6/4 10:47
 **/
@Component
public class JwtUtil {

    private static RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        JwtUtil.redisTemplate = redisTemplate;
    }
}

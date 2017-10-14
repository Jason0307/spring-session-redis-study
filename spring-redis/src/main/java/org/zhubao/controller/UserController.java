package org.zhubao.controller;

import static org.zhubao.constants.Constants.USER_PREFIX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.zhubao.entity.User;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private RedisTemplate<String, User> redisTemplate;
    
    @GetMapping("/user/{id}")
    @Cacheable(value = "user-cache", key = "#id")
    public User getUser(@PathVariable(name = "id") String id) {
        log.info("Cache is not exist, get user info from database.");
        ValueOperations<String, User> hashOperations = redisTemplate.opsForValue();
        User user = hashOperations.get(USER_PREFIX + id);
        return user;
    }
}

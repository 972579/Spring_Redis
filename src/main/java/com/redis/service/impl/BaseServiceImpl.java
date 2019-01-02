package com.redis.service.impl;

import com.redis.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class BaseServiceImpl<T> implements BaseService<T> {

    protected ValueOperations<String, T> operations;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    private void setOptions() {
      operations = redisTemplate.opsForValue();
    }

    @Override
    public void save(String key, T value) {
        operations.set(key,value);
    }

    @Override
    public T get(String key) {
        return operations.get(key);
    }

    @Override
    public boolean update(String key, T value) {
        T obj = this.get(key);
        if (obj == null)
            return false;
        this.save(key,value);
        return true;
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }


}




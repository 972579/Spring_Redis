package com.redis.service;

import java.util.List;

public interface BaseService<T> {

    void save(String key,T value);

    T get(String key);

    boolean update(String key,T value);

    void delete(String key);
}

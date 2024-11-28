package kz.bitlab.middle02redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void cacheObject(String key, Object object, long timeout, TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key, object, timeout, timeUnit);
    }

    public void cachePopularObject(String key, String bookKey, long timeout, TimeUnit timeUnit){
        redisTemplate.opsForValue().setIfAbsent(key, bookKey, timeout, timeUnit);
    }

    public Object getCachedObject(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteCachedObject(String key){
        redisTemplate.delete(key);
    }

}

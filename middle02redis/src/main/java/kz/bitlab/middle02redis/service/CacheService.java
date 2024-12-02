package kz.bitlab.middle02redis.service;

import kz.bitlab.middle02redis.models.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void cacheObject(String key, Object book, long timeout, TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key, book, timeout, timeUnit);
    }

    public void cachePopularObject(Book book, long duration, TimeUnit timeUnit) {
        String popularBooksKey = "popular_books";
        redisTemplate.opsForZSet().add(popularBooksKey, book, 1);
        redisTemplate.expire(popularBooksKey, duration, timeUnit);
    }

    public Set<Object> getAllPopularBooks() {
        ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
        return zSetOps.range("popular_books", 0, -1);
    }

    public Object getCachedObject(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteCachedObject(String key){
        Object book = getCachedObject(key);
        redisTemplate.opsForZSet().remove("popular_books", book);
        redisTemplate.delete(key);
    }
}

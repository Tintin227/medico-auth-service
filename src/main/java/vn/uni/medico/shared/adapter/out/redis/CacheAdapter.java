package vn.uni.medico.shared.adapter.out.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import vn.uni.medico.shared.application.port.out.CacheService;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class CacheAdapter implements CacheService {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value, Integer timeout) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void setHash(String hashName, String key, String value) {
        redisTemplate.opsForHash().put(hashName, key, value);
        redisTemplate.expire(key, 60*60, TimeUnit.SECONDS);
    }

    @Override
    public String getValue(String hashName, String key) {
        String value = (String) redisTemplate.opsForHash().get(hashName, key);
        return value;
    }
}

package vn.uni.medico.shared.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.jedis.pool.max-active:100}")
    private Integer maxActive;

    @Value("${spring.redis.jedis.pool.max-idle:10}")
    private Integer maxIdle;

    @Value("${spring.redis.jedis.pool.min-idle:1}")
    private Integer minIdle;

    @Value("${spring.redis.sentinel.master}")
    private String master;

    @Value("${spring.redis.sentinel.nodes}")
    private String[] nodes;

    @Value("${spring.redis.database}")
    private Integer database;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setTestOnBorrow(false);
        poolConfig.setTestOnReturn(false);

        //setinel config
        RedisSentinelConfiguration sentinelConfiguration = new RedisSentinelConfiguration().master(master);
        if (nodes != null) {
            for (String node : nodes) {
                sentinelConfiguration = sentinelConfiguration
                        .sentinel(node.split(":")[0],
                                Integer.valueOf(node.split(":")[1]));
            }
            sentinelConfiguration.setPassword(this.password);
        }

        sentinelConfiguration.setDatabase(database);

        return new JedisConnectionFactory(sentinelConfiguration, poolConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setDefaultSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

}

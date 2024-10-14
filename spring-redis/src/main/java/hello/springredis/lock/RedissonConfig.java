package hello.springredis.lock;

import lombok.Value;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    private static final String REDIS_HOST = "localhost";

    private static final int REDIS_PORT = 6379;

    private static final String REDISSON_HOST_PREFIX = "redis://";

    @Bean
    public RedissonClient redissonClient() {
        RedissonClient redisson = null;
        Config config = new Config();
        config.useSingleServer().setAddress(REDISSON_HOST_PREFIX + REDIS_HOST + ":" + REDIS_PORT);
        redisson = Redisson.create(config);
        return redisson;
    }
}
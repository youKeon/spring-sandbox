package hello.springredis.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class RedisPubSubTest {
    @Autowired RedisMessagePublisher redisMessagePublisher;
    @Autowired RedisMessageSubscriber redisMessageSubscriber;

    @Test
    void redisPubSubTest() {
        String message = "Message " + UUID.randomUUID();
        redisMessagePublisher.publish(message);
        Assertions.assertThat(RedisMessageSubscriber.messageList.get(0).contains(message)).isTrue();
    }
}

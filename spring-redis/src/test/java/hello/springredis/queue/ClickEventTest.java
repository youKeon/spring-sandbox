package hello.springredis.queue;

import hello.springredis.queue.v1.Click;
import hello.springredis.queue.v1.ClickRepository;
import hello.springredis.queue.v1.ClickService;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClickEventTest {

    @Autowired
    ClickService clickService;
    @Autowired
    ClickRepository clickRepository;

    @Test
    @DisplayName("test")
    void test() {
        Click click = clickRepository.save(new Click());
        Optional<Click> savedClick = clickRepository.findById(click.getId());
        Assertions.assertThat(savedClick).isNotNull();

        clickService.click(savedClick.get().getId());
    }
}

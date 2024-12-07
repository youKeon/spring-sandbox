package hello.springredis.queue.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClickEventListener {
    private final ClickRepository clickRepository;

    @EventListener
    public void handleClickEvent(ClickEvent event) {
        Click click = clickRepository.findById(event.id()).orElse(new Click());
        log.info("click event publish ::: info={}", click);
        click.increase();
    }
}

package hello.springredis.queue.v1;

import jodd.cli.Cli;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClickService {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void click(Long clickId) {
        applicationEventPublisher.publishEvent(new ClickEvent(clickId));
    }
}

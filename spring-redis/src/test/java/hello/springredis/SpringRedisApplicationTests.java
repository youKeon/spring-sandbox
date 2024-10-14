package hello.springredis;

import hello.springredis.lock.Ticket;
import hello.springredis.lock.TicketRepository;
import hello.springredis.lock.TicketService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class SpringRedisApplicationTests {
    @Autowired TicketRepository ticketRepository;

    @Autowired TicketService ticketService;

    @Test
    @DisplayName("test")
    void test() throws InterruptedException {
        Long tickId = 1L;
        ticketRepository.save(new Ticket(tickId, 100L));

        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    ticketService.buyTicket(tickId);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Ticket ticket = ticketRepository.findById(tickId).orElseThrow(RuntimeException::new);
        Assertions.assertThat(ticket.getQuantity()).isZero();
    }
}

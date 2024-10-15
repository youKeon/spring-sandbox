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
    @DisplayName("락을 적용한 메서드는 정확히 100개가 차감된다.")
    void lockTest() throws InterruptedException {
        Ticket ticket = ticketRepository.save(new Ticket(100L));

        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    ticketService.decrementWithLock(ticket.getId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Ticket persistTicket = ticketRepository.findById(ticket.getId()).orElseThrow(RuntimeException::new);
        Assertions.assertThat(persistTicket.getQuantity()).isZero();
    }

    @Test
    @DisplayName("락을 적용하지 않은 메서드는 동시성 문제가 발생한다.")
    void unLockTest() throws InterruptedException {
        Ticket ticket = ticketRepository.save(new Ticket(100L));

        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    ticketService.decrement(ticket.getId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Ticket persistTicket = ticketRepository.findById(ticket.getId()).orElseThrow(RuntimeException::new);
        Assertions.assertThat(persistTicket.getQuantity()).isNotZero();
    }
}

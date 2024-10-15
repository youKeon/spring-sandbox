package hello.springredis.lock.service;

import hello.springredis.lock.aspect.DistributedLock;
import hello.springredis.lock.domain.Ticket;
import hello.springredis.lock.domain.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;

    @Transactional
    @DistributedLock(key = "#lockName")
    public void decrementWithLock(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("account not found"));

        ticket.decrease();
    }

    @Transactional
    public void decrement(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("account not found"));

        ticket.decrease();
    }
}

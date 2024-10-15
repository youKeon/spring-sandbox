package hello.springredis.lock;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long quantity;

    public Ticket(Long quantity) {
        this.quantity = quantity;
    }

    public void increase() {
        validateCheck();
        this.quantity++;
    }

    public void decrease() {
        validateCheck();
        this.quantity--;
    }

    private void validateCheck() {
        if (quantity <= 0) throw new RuntimeException("잔여 수량이 없습니다.");
    }
}

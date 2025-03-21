package hello.springjpa;

import hello.springjpa.fetch_join.ChildA;
import hello.springjpa.fetch_join.ChildB;
import hello.springjpa.fetch_join.Parent;
import hello.springjpa.fetch_join.ParentRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ManyFetchTest {
    @Autowired
    EntityManager em;

    @Autowired
    ParentRepository parentRepository;

    @Test
    void fetchJoin_CartesianProduct_확인() {
        Parent parent = new Parent("Parent1");
        em.persist(parent);

        for (int i = 1; i <= 3; i++) {
            em.persist(new ChildA("ChildA" + i, parent));
        }

        for (int i = 1; i <= 4; i++) {
            em.persist(new ChildB("ChildB" + i, parent));
        }

        em.flush();
        em.clear();

        // ✅ Fetch Join 실행
        List<Parent> result = parentRepository.findAllWithBothCollections();
        System.out.println("조회된 row 수: " + result.size());

        for (Parent p : result) {
            System.out.println("Parent: " + p.getName());
        }
    }
}

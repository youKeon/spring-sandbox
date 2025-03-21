package hello.springjpa.np1;

import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner, DisposableBean {

    private final EntityManager em;

    private final List<Team> createdTeams = new ArrayList<>();
    private final List<Member> createdMembers = new ArrayList<>();

    @Override
    @Transactional
    public void run(String... args) {
        System.out.println("==== Data Init ====");
        for (int i = 1; i <= 3; i++) {
            Team team = new Team("Team" + i);
            em.persist(team);
            createdTeams.add(team);

            for (int j = 1; j <= 3; j++) {
                Member member = new Member("Member" + i + "-" + j, team);
                em.persist(member);
                createdMembers.add(member);
            }
        }
    }

    @Override
    @Transactional
    public void destroy() {
        System.out.println("==== Data Destroy ====");
        for (Member member : createdMembers) {
            em.remove(em.contains(member) ? member : em.merge(member));
        }
        for (Team team : createdTeams) {
            em.remove(em.contains(team) ? team : em.merge(team));
        }
    }
}
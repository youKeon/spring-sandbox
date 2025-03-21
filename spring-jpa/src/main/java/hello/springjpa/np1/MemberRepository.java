package hello.springjpa.np1;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m JOIN FETCH m.team")
    List<Member> findAllWithTeam();
}
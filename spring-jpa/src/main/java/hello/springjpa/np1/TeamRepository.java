package hello.springjpa.np1;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("SELECT t FROM Team t")
    List<Team> findAllWithoutMembers();

    @Query("SELECT t FROM Team t JOIN FETCH t.members")
    List<Team> findAllWithMembers();

    @Query("SELECT t FROM Team t JOIN FETCH t.members")
    List<Team> findWithMembersWithoutDistinct();

    // Fetch join - DISTINCT 사용
    @Query("SELECT DISTINCT t FROM Team t JOIN FETCH t.members")
    List<Team> findWithMembersWithDistinct();
}
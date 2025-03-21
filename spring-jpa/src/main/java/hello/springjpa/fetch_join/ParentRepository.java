package hello.springjpa.fetch_join;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParentRepository extends JpaRepository<Parent, Long> {

    @Query("SELECT p FROM Parent p " +
        "JOIN FETCH p.childAs " +
        "JOIN FETCH p.childBs")
    List<Parent> findAllWithBothCollections();
}
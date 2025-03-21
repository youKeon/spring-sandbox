package hello.springjpa;

import hello.springjpa.np1.Member;
import hello.springjpa.np1.MemberRepository;
import hello.springjpa.np1.Team;
import hello.springjpa.np1.TeamRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class NP1Test {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @BeforeEach
    void setUp() {
        for (int i = 1; i <= 3; i++) {
            Team team = new Team("Team" + i);
            teamRepository.save(team);

            for (int j = 1; j <= 3; j++) {
                memberRepository.save(new Member("Member" + i + "-" + j, team));
            }
        }
    }

    @Test
    void oneToManyNP1() {
        List<Team> team = teamRepository.findAllWithoutMembers();
        System.out.println("::: N + 1 발생 :::");
        for (Team t : team) {
            for (Member m : t.getMembers()) {
                m.getName();
            }
        }
    }

    @Test
    void oneToManyNP1FetchJoin() {
        System.out.println("==== Team -> Member: Fetch Join 해결 ====");
        List<Team> teams = teamRepository.findAllWithMembers();
        for (Team team : teams) {
            System.out.println("Team: " + team.getName() + ", Members: " +
                team.getMembers().stream().map(Member::getName).toList());
        }
    }

    @Test
    void np1() {
        System.out.println("==== 사용자 조회 ====");
        List<Member> members = memberRepository.findAll(); // 지연 로딩
        System.out.println("==== N + 1 발생 ====");
//        for (Member m : members) {
//            System.out.println("Member: " + m.getName() + ", Team: " + m.getTeam().getName());
//        }

    }

    @Test
    void fetch_join_적용으로_해결() {
        System.out.println("==== Fetch Join 사용 ====");
        List<Member> members = memberRepository.findAllWithTeam(); // Fetch Join
        for (Member m : members) {
            System.out.println("Member: " + m.getName() + ", Team: " + m.getTeam().getName());
        }
    }

    @Test
    void fetchJoin_WithoutDistinct() {
        System.out.println("==== Fetch Join without DISTINCT ====");
        List<Team> teams = teamRepository.findWithMembersWithoutDistinct();
        printTeams(teams);
    }

    @Test
    void fetchJoin_WithDistinct() {
        System.out.println("==== Fetch Join with DISTINCT ====");
        List<Team> teams = teamRepository.findWithMembersWithDistinct();
        printTeams(teams);
    }

    private void printTeams(List<Team> teams) {
        System.out.println("Teams.size = " + teams.size());
        Set<Integer> uniqueTeamIds = teams.stream()
            .map(System::identityHashCode) // 실제 객체 비교용
            .collect(Collectors.toSet());
        System.out.println("Unique team object count = " + uniqueTeamIds.size());

        for (Team team : teams) {
            System.out.println("Team: " + team.getName() +
                " / Members: " + team.getMembers().stream()
                .map(Member::getName)
                .toList());
        }
    }
}

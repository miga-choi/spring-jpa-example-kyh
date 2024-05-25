package jpabook.start;

import javax.persistence.*;
import java.util.List;

/**
 * @author holyeye
 */
public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook"); // 엔티티 매니저 팩토리 생성
        EntityManager em = emf.createEntityManager(); // 엔티티 매니저 생성
        EntityTransaction tx = em.getTransaction(); // 트랜잭션 기능 획득

        try {
            tx.begin(); // 트랜잭션 시작
            // testSave(em);
            // testSelect1(em);
            // testSelect2(em);
            // testUpdate(em);
            // testDelete(em);
            // biDirection(em);
            // testSave2(em);
            // testSaveNonOwner(em);
            // test_ORM_양방향(em);
            test_ORM_양방향_리팩토링(em);
            tx.commit(); // 트랜잭션 커밋
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); // 트랜잭션 롤백
        } finally {
            em.close(); // 엔티티 매니저 종료
        }

        emf.close(); // 엔티티 매니저 팩토리 종료
    }

    public static void testSave(EntityManager em) {
        // 팀1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        // 회원1 저장
        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1); // 연관관계 설정 member1 -> team1
        em.persist(member1);

        // 회원2 저장
        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team1); // 연관관계 설정 member2 -> team1
        em.persist(member2);
    }

    /**
     * 조회
     * 연관관계가 있는 엔티티를 조회하는 방법은 크게 2가지다.
     * 1. 객체 그래프 탐색 (객체 연관관계를 사용한 조회)
     * 2. 객체지향 쿼리 사용 (JPQL)
     */
    // 1. 객체 그래프 탐색
    public static void testSelect1(EntityManager em) {
        Member member = em.find(Member.class, "member1");
        Team team = member.getTeam();
        System.out.println("팀 이름 = " + team.getName());
        // 출력 결과: 팀 이름 = 팀1
    }

    // 2. 객체지향 쿼리 사용 (JPQL)
    public static void testSelect2(EntityManager em) {
        String jpq1 = "select m from Member m join m.team t where t.name=:teamName";
        List<Member> resultList = em.createQuery(jpq1, Member.class)
                .setParameter("teamName", "팀1")
                .getResultList();
        for (Member member : resultList) {
            System.out.println("[query] member.username = " + member.getUsername());
        }
        // 출력 결과: [query] member.username = 회원1
        // 출력 결과: [query] member.username = 회원2
    }

    public static void testUpdate(EntityManager em) {
        // 새로운 팀2
        Team team2 = new Team("team2", "팀2");
        em.persist(team2);

        // 회원1에 새로운 팀2 설정
        Member member = em.find(Member.class, "member1");
        member.setTeam(team2);
    }

    public static void testDelete(EntityManager em) {
        Member member1 = em.find(Member.class, "member1");
        member1.setTeam(null); // 연관관계 제거

        Team team1 = em.find(Team.class, "team1");
        Member member2 = em.find(Member.class, "member2");
        member2.setTeam(null); // 회원2 연관관계 제거
        em.remove(team1);
    }

    public static void biDirection(EntityManager em) {
        Team team = em.find(Team.class, "team1");
        List<Member> members = team.getMembers(); // (팀 -> 회원) 객체 그래프 탐색

        for (Member member : members) {
            System.out.println("member.username = " + member.getUsername());
            // 결과: member.username = 회원1
            // 결과: member.username = 회원2
        }
    }


    /**
     * 양방향 연관관계의 주의점:
     * 양방향 연관관계를 설정하고 가장 흔히 하는 실수는 연관관계의 주인에는 값을 입력하지 않고.
     * 주인이 아닌 곳에만 값을 입력하는 것이다.
     * 데이터베이스에 외래 키 값이 정상적으로 저장되지 않으면 이것부터 의심해보자.
     */
    // 양방향 연관관계 저장
    public static void testSave2(EntityManager em) {
        // 팀1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        // 회원1 저장
        Member member1 = new Member("member1", "회원1");
        team1.getMembers().add(member1); // 무시 (연관관계의 주인이 아님)
        member1.setTeam(team1); // 연관관계 설정 member1 -> team1
        em.persist(member1);

        // 회원2 저장
        Member member2 = new Member("member2", "회원2");
        team1.getMembers().add(member2); // 무시 (연관관계의 주인이 아님)
        member2.setTeam(team1); // 연관관계 설정 member2 -> team1
        em.persist(member2);
    }

    // 주인이 아닌 곳에만 값을 설정하는 경우
    public static void testSaveNonOwner(EntityManager em) {
        // 회원1 저장
        Member member1 = new Member("member1", "회원1");
        em.persist(member1);

        // 회원2 저장
        Member member2 = new Member("member2", "회원2");
        em.persist(member2);

        Team team1 = new Team("team1", "팀1");

        // 주인이 아닌 곳만 연관관계 설정
        team1.getMembers().add(member1);
        team1.getMembers().add(member2);

        em.persist(team1);
    }

    /**
     * 그렇다면 정말 연관관계의 주인에만 값을 저장하고 주인이 아닌 곳에는 값을 저장하지 않아도 될까?
     * 사실은 객체 관점에서 양쪽 방향에 모두 값을 입력해주는 것이 가장 안전하다.
     * 양쪽 방향 모두 값을 입력하지 않으면 JPA를 사용하지 않는 순수한 객체 상태에서 심각한 문제가 발생할 수 있다.
     * ------------------------------------------------------------------------
     * 예를 들어 JPA를 사용하고 않고 엔티티에 대한 테스트 코드를 작성한다고 가정해보자.
     * ORM은 객체와 관계형 데이터베이스 둘 다 중요하다.
     * 데이터베이스뿐만 아니라 객체도 함께 고려해야 한다.
     */
    public static void test_순수한객체_양방향1() {
        // 팀1
        Team team1 = new Team("team1", "팀1");

        // 회원1
        Member member1 = new Member("member1", "회원1");

        // 회원2
        Member member2 = new Member("member2", "회원2");

        member1.setTeam(team1); // 연관관계 설정 member1 -> team1
        member2.setTeam(team1); // 연관관계 설정 member2 -> team1

        List<Member> members = team1.getMembers();
        System.out.println("members.size = " + members.size());
        // 결과: members.size = 0
    }

    /**
     * 양방향은 양쪽다 관계를 설정해야 한다.
     * 회원 -> 팀을 성정 하면 반대방향인 팀 -> 회원도 설정해야 한다.
     */
    public static void test_순수한객체_양방향2() {
        // 팀1
        Team team1 = new Team("team1", "팀1");

        // 회원1
        Member member1 = new Member("member1", "회원1");

        // 회원2
        Member member2 = new Member("member2", "회원2");

        member1.setTeam(team1);          // 연관관계 설정 member1 -> team1
        team1.getMembers().add(member1); // 연관관계 설정 team1 -> member1

        member2.setTeam(team1);          // 연관관계 설정 member2 -> team1
        team1.getMembers().add(member2); // 연관관계 설정 team1 -> member2

        List<Member> members = team1.getMembers();
        System.out.println("members.size = " + members.size());
        // 결과: members.size = 2
    }

    /**
     * 양쪽에 연관관계를 설정했다. 따라서 순수한 객체 상태에서도 동작하며, 테이블의 외래 키도 정상 입력된다.
     * 물론 외래 키의 값은 연관관계의 주인인 Member.team 값을 사용한다.
     * - Member.team: 연관관계의 주인, 이 값으로 외래 키를 관리한다.
     * - Team.members: 연관관계의 주인이 아니다. 따라서 저장 시에 사용되지 않는다.
     * 결론: 객체의 양방향 연관관계는 양쪽 모두 관계를 맺어주자.
     */
    public static void test_ORM_양방향(EntityManager em) {
        // 팀1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        // 회원1
        Member member1 = new Member("member1", "회원1");

        // 양방향 연관관계 설장
        member1.setTeam(team1);          // 연관관계 설정 member1 -> team1
        team1.getMembers().add(member1); // 연관관계 설정 team1 -> member1
        em.persist(member1);

        // 회원2
        Member member2 = new Member("member2", "회원2");

        // 양방향 연관관계 설장
        member2.setTeam(team1);          // 연관관계 설정 member2 -> team1
        team1.getMembers().add(member2); // 연관관계 설정 team1 -> member2
        em.persist(member2);
    }

    public static void test_ORM_양방향_리팩토링(EntityManager em) {
        // 팀1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        // 회원1
        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1); // 연관관계 설정 member1 -> team1
        em.persist(member1);

        // 회원2
        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team1); // 연관관계 설정 member2 -> team1
        em.persist(member2);
    }

}

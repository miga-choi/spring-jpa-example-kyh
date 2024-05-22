package jpabook.start;

import javax.persistence.*;
import java.util.Date;
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
//            testSave(em);
//            testSelect1(em);
//            testSelect2(em);
//            testUpdate(em);
//            testDelete(em);
//            biDirection(em);
            testSave2(em);
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
        member1.setTemm(team1); // 연관관계 설정 member1 -> team1
        em.persist(member1);

        // 회원2 저장
        Member member2 = new Member("member2", "회원2");
        member2.setTemm(team1); // 연관관계 설정 member2 -> team1
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
        member.setTemm(team2);
    }

    public static void testDelete(EntityManager em) {
        Member member1 = em.find(Member.class, "member1");
        member1.setTemm(null); // 연관관계 제거

        Team team1 = em.find(Team.class, "team1");
        Member member2 = em.find(Member.class, "member2");
        member2.setTemm(null); // 회원2 연관관계 제거
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

    // 양방향 연관관계 저장
    public static void testSave2(EntityManager em) {
        // 팀1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        // 회원1 저장
        Member member1 = new Member("member1", "회원1");
        member1.setTemm(team1); // 연관관계 설정 member1 -> team1
        em.persist(member1);

        // 회원2 저장
        Member member2 = new Member("member2", "회원2");
        member2.setTemm(team1); // 연관관계 설정 member2 -> team1
        em.persist(member1);
    }

}

package jpabook.start;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author holyeye
 */
public class JpaMain {

    public static void main(String[] args) {
        // 엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); // 엔티티 매니저 생성

        EntityTransaction tx = em.getTransaction(); // 트랜잭션 기능 획득

        try {
            tx.begin(); // 트랜잭션 시작
            // logic(em);  // 비즈니스 로직
            testSave(em);
            tx.commit();// 트랜잭션 커밋
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); // 트랜잭션 롤백
        } finally {
            em.close(); // 엔티티 매니저 종료
        }

        emf.close(); // 엔티티 매니저 팩토리 종료
    }

    public static void logic(EntityManager em) {
        String id = "id1";
        Member member = new Member();
        member.setId(id); // 기본 키 직접 할당
        member.setUsername("지한");
        member.setAge(2);

        // 등록
        em.persist(member);

        // 수정
        member.setAge(20);

        // 한 건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember=" + findMember.getUsername() + ", age=" + findMember.getAge());

        // 목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size=" + members.size());

        // 삭제
        em.remove(member);
    }

    public static void testSave(EntityManager em) {
        // 팀1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        // 회원1 저장
        Member member1 = new Member("member1", "회원1");
        member1.setTemm(team1); // 연관관계 설정 member1 -> team1
        member1.setAge(30);
        member1.setDescription("");
        member1.setCreatedDate(new Date());
        member1.setLastModifiedDate(new Date());
        member1.setRoleType(RoleType.USER);
        em.persist(member1);

        // 회원2 저장
        Member member2 = new Member("member2", "회원2");
        member2.setTemm(team1); // 연관관계 설정 member2 -> team1
        member2.setAge(30);
        member2.setDescription("");
        member2.setCreatedDate(new Date());
        member2.setLastModifiedDate(new Date());
        member2.setRoleType(RoleType.USER);
        em.persist(member2);
    }

}

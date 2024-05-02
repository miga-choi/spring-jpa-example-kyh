package jpabook.start;

import javax.persistence.*;
import java.util.List;

/**
 * @author holyeye
 */
public class JpaMain {

    public static void main(String[] args) {

        // [엔티티 매니저 팩토리] - 생성
        // 공장 만들기, 비용이 아주 많이 든다.
        // META-INF/persistence.xml에사 <persistence-unit name="jpabook"> 찾아서 생성
        // 엔티티 매니저 팩토리는 여러 스레드가 동시에 접근해도 안전 하므로 서로 다른 스례드 간에 공유해도 된다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

        // [엔티티 매니저] - 생성
        // 공장에서 엔티티 매니저 생성, 비용이 거의 안 든다.
        // 엔티티 매니저는 여러 스레드가 동시에 접근하면 공시성 문제가 발생하므로 스레드 간에 절대 공유하면 안 된다.
        EntityManager em = emf.createEntityManager();

        // [트랜잭션] - 획득
        EntityTransaction tx = em.getTransaction(); // 트랜잭션 API

        try {
            tx.begin();  // 트랜잭션 시작
            logic(em);   // 비즈니스 로직 실행
            tx.commit(); // 트랜잭션 커밋
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); // 예외 발생 시 트랜잭션 롤백
        } finally {
            em.close(); // [엔티티 매니저] - 종료
        }

        emf.close(); // [엔티티 매니저 팩토리] - 종료

    }

    // 비즈니스 로직
    public static void logic(EntityManager em) {

        String id = "id1";

        // 객체를 생성한 상태 (비영속)
        Member member = new Member();
        member.setId(id);
        member.setUsername("지한");
        member.setAge(2);

        // 등록
        // 객체를 저장한 상태 (영속)
        em.persist(member);

        // 회원 엔티티를 영속성 컨텍스트에서 분리, (준영속)
        // em.detach(member);

        // 영속성 컨텍스트를 닫는다.
        // em.close();

        // 영속성 컨텍스트를 초기화 한다.
        // em.clear();

        // 수정
        member.setAge(20);

        // 한 건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember=" + findMember.getUsername() + ", age=" + findMember.getAge());

        // 목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size=" + members.size());

        // JPQL
        Member findMemberById = em.createQuery("select m from Member m where m.id = 'id1'", Member.class).getSingleResult();
        System.out.println("[findMemberById] name=" + findMemberById.getUsername() + ", age=" + findMemberById.getAge());

        // 삭제
        // 객체를 삭제한 상태 (삭제)
        em.remove(member);

    }

}

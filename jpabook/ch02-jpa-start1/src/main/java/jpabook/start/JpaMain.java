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

    public static void lifecycle(EntityManager em) {
        // 비영속
        // 객체를 생성한 상태 (비영속)
        Member member = new Member();
        member.setId("member1");
        member.setUsername("회원1");

        // 영속
        // 객체를 저장한 상태 (영속)
        em.persist(member);

        // 준영속
        // 회원 엔티티를 영속성 컨텍스트에서 분리, 준영속 상태
        em.detach(member);

        // 삭제
        // 객체를 삭제한 상태 (삭제)
        em.remove(member);
    }

    public static void findFromCache(EntityManager em) {
        // 엔티티를 생성한 상태 (비영속)
        Member member = new Member();
        member.setId("member1");
        member.setUsername("회원1");

        // 엔티티를 영속
        em.persist(member);

        // 1차 캐시에서 조회
        // "영속 컨텍스트 (EntityManager)"에서 식별자 값으로 엔티티를 찾는다.
        // 만약 찾는 엔티티가 있으면 데이터베이스를 조회하지 않고 메모리에 있는 1차 캐시에서 엔티티를 조회한다.
        Member member1 = em.find(Member.class, "member1");

        // "데이터베이스 (Database)"에서 조회
        // 만약 em.find()를 호출 했는데 엔티티가 1차 캐시에 없으면 엔티티 매니저는 데이터베이스를 조회해서 엔티티를 생성한다.
        // 그리고 1차 캐시에 저장한 후에 영속 상태의 엔티티를 반환한다.
        Member member2 = em.find(Member.class, "member2");
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

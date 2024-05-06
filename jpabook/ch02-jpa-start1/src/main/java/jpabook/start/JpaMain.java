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

    public static void ensuringIdentityOfPersistentEntities(EntityManager em) {
        Member a = em.find(Member.class, "member1");
        Member b = em.find(Member.class, "member1");

        // em.find(Member.class, "member1")를 반복해서 호출해도 영속성 컨텍스트는 1차 캐시에 있는 같은 엔티티 인스턴스를 반환한다.
        // 따라서 둘은 같은 인스턴스고 결과는 당연히 참이다.
        // 따라서 영속성 컨텍스트는 성능상 이점과 엔티티의 동일성을 보장한다.
        System.out.println(a == b); // 동일성 비교
    }

    public static void create(EntityManagerFactory emf) {
        Member memberA = new Member();
        memberA.setId("memberA");
        memberA.setUsername("회원A");

        Member memberB = new Member();
        memberB.setId("memberB");
        memberB.setUsername("회원B");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        // 엔티티 매니저는 데이터 변경 시 트랜잭션을 시작해야 한다.
        transaction.begin(); // [트랜잭션] 시작

        em.persist(memberA);
        em.persist(memberB);
        // 여기까지 INSERT SQL을 데이터베이스에 보내지 않는다.

        // 커밋하는 순간 데이터베이스에 INSERT SQL을 보낸다.
        transaction.commit(); // [트랜잭션] 커밋
    }

    public static void update(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin(); // [트랜잭션] 시작

        // 영속 엔티티 조회
        Member memberA = em.find(Member.class, "memberA");

        // 영속 엔티티 데이터 수정
        // 엔티티의 변경 사항을 데이터 베이스에 자동으로 반영하는 기능 - 변경 감지 (dirty checking)
        // 변경 감지는 영속성 컨텍스트가 관리하는 영속 상태의 엔티티에만 적용된다.
        memberA.setUsername("hi");
        memberA.setAge(10);

        transaction.commit(); // [트랜잭션] 커밋
    }

    public static void delete(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin(); // [트랜잭션] 시작

        // 삭제 대상 엔티티 조회
        Member memberA = em.find(Member.class, "memberA");

        // 엔티티 삭제
        em.remove(memberA);

        transaction.commit(); // [트랜잭션] 커밋
    }

    public static void flush(EntityManager em) {
        // 플러시(flush())는 영속성 컨텍스트의 변경 내용을 데이터베이스에 반영한다.
        // 플러시를 실행하면 구체적으로 다음과 같은 일이 일어난다.
        //   1. 변경 감지가 동작해서 영속성 컨텍스트에 있는 모든 엔티티를 스냅샷과 비교해서 수정된 엔티티를 찾는다.
        //      수정된 엔티티는 수정 쿼리를 만들어 쓰기 지연 SQL 저장소에 등록한다.
        //   2. 쓰기 지연 SQL 저장소의 쿼리를 데이터 베이스에 전송한다(등록, 수정, 삭제 쿼리).
        //
        // 영속성 컨텍스트를 플러시하는 방법은 3가지다.
        //   1. em.flush()를 직접 호출한다.
        //   2. 트랜잭션 커밋 시 플러시가 자동 호출된다.
        //   3. JPQL 쿼리 실행 시 플러시가 자동 호출된다.

        // 플러시 모드 옵션
        // 엔티티 매니저에 플러시 모드를 직접 지정하려면 javax.persistence.FlushModeType을 사용하면 된다.
        //   1. FlushModeType.AUTO: 커밋이나 쿼리를 싱행할 때 플러시 (기본값)
        //   2. FlushModeType.COMMIT: 커밋할 때만 플러시
        em.setFlushMode(FlushModeType.COMMIT); // 플러시 모드 설정
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

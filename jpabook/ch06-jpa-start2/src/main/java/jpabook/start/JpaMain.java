package jpabook.start;

import javax.persistence.*;

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
            testSave(em);
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
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        Team team1 = new Team("team1");
        team1.getMembers().add(member1);
        team1.getMembers().add(member2);

        em.persist(member1); // INSERT-member1
        em.persist(member2); // INSERT-member2
        em.persist(team1); // INSERT-team1, UPDATE-member1.fk, UPDATE-member2.fk

        /*
            실행한 결과 SQL:
            insert into Member (MEMBER_ID, username) values (null, ?)
            insert into Member (MEMBER_ID, username) values (null, ?)
            insert into Team (TEAM_ID, name) values (null, ?)
            update Member set TEAM_ID=? where MEMBER_ID=?
            update Member set TEAM_ID=? where MEMBER_ID=?
         */
        /*
            Member 엔티티는 Team 엔티티를 모른다. 그리고 연관관계에 대한 정보는 Team 엔티티의 members가 관리한다.
            따라서 Member 엔티티를 저장할 때는 MEMBER 테이블의 TEAM_ID 외래 키에 아무 값도 저장되지 않는다.
            대신 Team 엔티티를 저장할 때 Team.members의 참조 값을 확인해서 회원 테이블에 있는 TEAM_ID 외래 키를 업데이트 한다.
         */
        /*
            일대다 단방향 매핑보다는 다대일 양방향 매핑을 사용하자:
            일대다 단방향 매핑을 사용하면 엔티티를 매핑한 테이블이 아닌 다른 테이블의 외래 키를 관리해야 한다.
            이것은 성능 문제도 있지만 관리도 부담스럽다.
            문제를 해결하는 좋은 방법은 일대다 단방향 매핑 대신에 다대일 양방향 매핑을 사용하는 것이다.
            다대일 양방향 매핑은 관리해야 하는 외래 키가 본인 테이블에 있다.
            따라서 일대다 단방향 매핑 같은 문제가 발행하지 않는다.
            두 매핑의 테이블 모양은 완전히 같으므로 엔티티만 약간 수정하면 된다.
            상황에 따라 다르곘지만 일대다 단방향 매핑보다는 다대일 양방향 매핑을 권장한다.
         */
    }

}

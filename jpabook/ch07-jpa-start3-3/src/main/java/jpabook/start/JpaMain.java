package jpabook.start;

import javax.persistence.*;

/*
    복합 키: 식별 관계 매핑
    ------------------------------------------------------------------------
    복합 키와 식별 관계를 알아보자.

    ex) PARENT [1:N] CHILD [1:N] GRANDCHILD
        PARENT: PARENT_ID (PK), NAME
        CHILD: PARENT_ID (PK, FK), CHILD_ID (PK), NAME
        GRANDCHILD: PARENT_ID (PK, FK), CHILD_ID (PK, FK), GRANDCHILD_ID (PK), NAME

    설명)
        위 예제를 보면 부모, 자식, 손자까지 계속 기본 키를 전달하는 식별 관계다.
        식별 관계에서 자식 테이블은 부모 테이블의 기본 키를 포함해서 복합 키를 구성해야 하므로
        @IdClass나 @EmbeddedId를 사용해서 식별자를 매핑해야 한다.
    ------------------------------------------------------------------------
 */
public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook"); // 엔티티 매니저 팩토리 생성
        EntityManager em = emf.createEntityManager(); // 엔티티 매니저 생성
        EntityTransaction tx = em.getTransaction(); // 트랜잭션 기능 획득

        try {
            tx.begin(); // 트랜잭션 시작
            tx.commit(); // 트랜잭션 커밋
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); // 트랜잭션 롤백
        } finally {
            em.close(); // 엔티티 매니저 종료
        }

        emf.close(); // 엔티티 매니저 팩토리 종료
    }

}

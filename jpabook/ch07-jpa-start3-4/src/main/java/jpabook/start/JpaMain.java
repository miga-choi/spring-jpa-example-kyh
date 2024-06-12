package jpabook.start;

import javax.persistence.*;

/*
    비식별 관계로 구현
    ------------------------------------------------------------------------
    아래 식별 관계 테이블을 비 식별 관계로 변경 해보자.

    식별 관계 테이블)
        PARENT [1:N] CHILD [1:N] GRANDCHILD
        - PARENT: PARENT_ID (PK), NAME
        - CHILD: PARENT_ID (PK, FK), CHILD_ID (PK), NAME
        - GRANDCHILD: PARENT_ID (PK, FK), CHILD_ID (PK, FK), GRANDCHILD_ID (PK), NAME

    비 식별 관계 테이블)
        PARENT [1:N] CHILD [1:N] GRANDCHILD
        - PARENT: PARENT_ID (PK), NAME
        - CHILD: CHILD_ID (PK), PARENT_ID (FK), NAME
        - GRANDCHILD: GRANDCHILD_ID (PK), CHILD_ID (FK), NAME
    ------------------------------------------------------------------------
    식별 관계의 복합 키를 사용한 코드와 비교하면 매핑도 쉽고 코드도 단순하다.
    그리고 복합 키가 없으므로 복합 키 클래스를 만들지 않아도 된다.
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

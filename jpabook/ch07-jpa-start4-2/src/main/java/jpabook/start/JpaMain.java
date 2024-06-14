package jpabook.start;

import javax.persistence.*;

/*
    일대다 조인 테이블
    ------------------------------------------------------------------------------------------------------------
    일대다 관계를 만들려면 조인 테이블의 컬럼 중 다(N)와 관련된 컬럼인 CHILD_ID에
    유니크 제약조건을 걸어야 한다 (CHILD_ID는 기본 키이므로 유니크 제약조건이 걸려 있다).

    에제1) 조인 컬럼
        PARENT [1:N] CHILD
        - PARENT: PARENT_ID (PK), NAME
        - CHILD: CHILD_ID (PK), PARENT_ID (FK), NAME

    에제2) 조인 테이블
        PARENT [1:N] PARENT_CHILD [1:1] CHILD
        - PARENT: PARENT_ID (PK), NAME
        - PARENT_CHILD: CHILD_ID (PK, FK), PARENT_ID (FK)
        - CHILD: CHILD_ID (PK), NAME
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

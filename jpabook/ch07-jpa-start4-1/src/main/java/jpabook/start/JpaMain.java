package jpabook.start;

import javax.persistence.*;

/*
    일대일 조인 테이블
    ------------------------------------------------------------------------------------------------------------
    일대일 관계를 만들려면 조인 테이블의 외래 키 컬럼 각각에 총 2개의 유니크
    제약조건을 걸어야 한다 (PARENT_ID는 기본 키이므로 유니크 제약조건이 걸려 있다).

    예제1) 조인 컬럼
        PARENT [1:1] CHILD
        - PARENT: PARENT_ID (PK), NAME
        - CHILD: CHILD_ID (PK), PARENT_ID (FK, UNI), NAME

    예제2) 조인 테이블
        PARENT [1:N] PARENT_CHILD [1:N] CHILD
        - PARENT: PARENT_ID (PK), NAME
        - PARENT_CHILD: PARENT_ID (PK, FK), CHILD_ID (UNI, FK)
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

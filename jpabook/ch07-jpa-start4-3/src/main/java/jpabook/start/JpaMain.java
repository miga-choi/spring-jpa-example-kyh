package jpabook.start;

import javax.persistence.*;

/*
    일대다 조인 테이블
    ------------------------------------------------------------------------------------------------------------
    다대일은 일대다에서 방향만 반대이므로 조인 테이블 모양은 일대다에서 설명한 예제와 같다.
    다대일, 일대다 양방향 관계로 매핑해보자.

    에제1) 조인 컬럼
        PARENT <-[1:N]-> CHILD
        - PARENT: PARENT_ID (PK), NAME
        - CHILD: CHILD_ID (PK), PARENT_ID (FK), NAME

    에제2) 조인 테이블
        PARENT <-[1:N]-> PARENT_CHILD <-[1:1]-> CHILD
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

package jpabook.start;

import javax.persistence.*;

/*
    엔티티 하나에 여러 테이블 매핑
    ------------------------------------------------------------------------------------------------------------
    잘 사용하지는 않지만 @SecondaryTable을 사용하면 한 엔티티에 여러 테이블을 매핑할 수 있다.

    에제)
        BOARD(Entity) --> BOARD(Table)
        BOARD(Entity) --> BOARD_DETAIL(Table)
        BOARD(Table) -[1:1]-> BOARD_DETAIL(Table)
        - BOARD(Entity): Long id, String title, String content
        - BOARD(Table): BOARD_ID (PK), title
        - BOARD_DETAIL(Table): BOARD_DETAIL_ID (PK, FK), content
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

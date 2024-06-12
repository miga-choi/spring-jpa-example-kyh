package jpabook.start;

import javax.persistence.*;

/*
    일대일 식별 관계
    ------------------------------------------------------------------------
    일대일 식별 관계는 조금 특별하다.

    ex)
        Board [1:1] BoardDetail
        - Board: BOARD_ID (PK), title
        - BoardDetail: BOARD_ID (PK, FK), content

    설명)
        위 예제를 보면 일대일 식별 관계는 자식 테이블의 기본 키 값으로 부모 테이블의 기본 키 값만 사용한다.
        그래서 부모 테이블의 기본 키가 복합 키가 아니면 자식 테이블의 기본 키는 복합 키로 구성하지 않아도 된다.
    ------------------------------------------------------------------------
    BoardDetail 처럼 식별자가 단순히 컬럼 하나면 @MapsId를 사용하고 속성 값은 비워두면 된다.
    이때 @MapsId는 @Id를 사용해서 식별자로 지정한 BoardDetail.boardId와 매핑된다.
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

    /*
        BoardDetail 처럼 식별자가 단순히 컬럼 하나면 @MapsId를 사용하고 속성 값은 비워두면 된다.
        이때 @MapsId는 @Id를 사용해서 식별자로 지정한 BoardDetail.boardId와 매핑된다.
     */
    public static void save(EntityManager em) {
        Board board = new Board();
        board.setTitle("제목");
        em.persist(board);

        BoardDetail boardDetail = new BoardDetail();
        boardDetail.setContent("내용");
        boardDetail.setBoard(board);
        em.persist(boardDetail);
    }

}

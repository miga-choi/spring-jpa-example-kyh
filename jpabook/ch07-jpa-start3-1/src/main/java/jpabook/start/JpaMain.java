package jpabook.start;

import javax.persistence.*;

/*
    식별 관계 vs 비식별 관계
    데이터베이스 테이블 사이에 관계는 외래 키가 기본 키가 포함되는지 여부에 따라 식별 관계와 비식별 관계로 구분한다.
    - 식별 관계 (Identifying Relationship)
    - 비식별 관계 (Non-Identifying Relationship)
    ------------------------------------------------------------------------
    식별 관계:
    식별 관계는 부모 테이블의 기본 키를 내려받아서 자식 테이블의 기본 키 + 외래 키로 사용하는 관계다.

    ex)
        PARENT: PARENT_ID (PK), NAME
        CHILD: PARENT_ID (PK, FK), CHILD_ID (PK), NAME

    PARENT 테이블의 기본 키 PARENT_ID를 받아서 CHILD 테이블의 기본 키(PK) + 외래 키(FK)로 사용한다.
    ------------------------------------------------------------------------
    비식별 관계:
    비식별 관계는 부모 테이블의 기본 키를 받아서 자식 테이블의 외래 키로만 사용하는 관계다.

    ex)
    필수적 비식별 관계:
        PARENT: PARENT_ID (PK), NAME
        CHILD: CHILD_ID (PK), PARENT_ID (FK), NAME
    선택적 비식별 관계:
        PARENT: PARENT_ID (PK), NAME
        CHILD: CHILD_ID (PK), PARENT_ID (FK), NAME

    PARENT 테이블의 기본 키 PARENT_ID를 받아서 CHILD 테이블의 외래 키(FK)로만 사용한다.
    비식별 관계는 외래 키에 NULL을 허용하는지에 따라 필수적 비식별 관계와 선택적 비식별 관계로 나눈다.

    - 필수적 비식별 관계 (Mandatory): 외래 키에 NULL을 허용하지 않는다. 연관관계를 필수적으로 맺어야 한다.
    - 선택적 비식별 관계 (Optional): 외래 키에 NULL을 허용한다. 연관관계를 맺을지 말지 선택할 수 있다.
    ------------------------------------------------------------------------
    데이터베이스 테이블을 설계할 때 식별 관계나 비식별 관계 중 하나를 선택해야 한다.
    최근에는 비식별 관계를 주로 사용하고 꼭 필요한 곳에만 식별 관계를 사용하는 추세다.
    JPA는 식별 관계와 비식별 관계를 모두 지원한다.
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

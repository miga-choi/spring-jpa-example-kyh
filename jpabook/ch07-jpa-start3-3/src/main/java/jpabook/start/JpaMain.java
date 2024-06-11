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
    @IdClass와 식별 관계:
        식별 관계는 기본 키와 외래 키를 같이 매핑해야 한다.
        따라서 식별자 매핑인 @Id와 연관관계 매핑인 @ManyToOne을 같이 사용하면 된다.
    ------------------------------------------------------------------------
    @EmbeddedId와 식별 관계:
        @EmbeddedId로 식별 관계를 구성할 때는 @MapsId를 사용해야 한다.

        @EmbeddedId는 식별 관계로 사용할 연관관계의 속성에 @MapsId를 사용하면 된다.

        @IdClass와 다른 점은 @Id 대신에 @MapsId를 사용한 점이다.
        @MapsId는 외래 키와 매핑한 연관관계를 기본 키에도 매핑하겠다는 뜻이다.
        @MapsId의 속성 값은 @EmbeddedId를 사용한 식별자 클래스의 기본 키 필드를 지정하면 된다.
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

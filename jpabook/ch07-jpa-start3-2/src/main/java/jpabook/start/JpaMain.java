package jpabook.start;

import javax.persistence.*;

/*
    복합 키: 비식별 관계 매핑
    1) 기본 키를 구성하는 컬럼이 하나면 다음처럼 단순하게 매핑한다.
        @Entity
        public class Hello {
            @Id
            private String id;
        }
    2) 둘 이상의 컬럼으로 구성된 복합 기본 키는 다음처럼 매핑하면 될 것 같지만 막상 해보면 매핑 오류가 발생한다.
       JPA에서 식별자를 둘 이상 사용하려면 별도의 식별자 클래스를 만들어야 한다.
        @Entity
        public class Hello {
            @Id
            private String id1;
            @Id
            private String id2;
        }
    ------------------------------------------------------------------------
    JPA는 영속성 컨텍스트에 엔티티를 보관할 때 엔티티의 식별자를 키로 사용한다.
    그리고 식별자를 구분하기 위해 equals와 hashCode를 사용해서 동등성 비교를 한다.
    그런데 식별자 필드가 하나일 때는 보통 자바의 기본 타입을 사용하므로 문제가 없지만,
    식별자 필드가 2개 이상이면 별도의 식별자 클래스를 만들고 그곳에 equals와 hashCode를 구현해야 한다.
    ------------------------------------------------------------------------
    JPA는 복합 키를 지원하기 위해 @IdClass와 @EmbeddedId 2가지 방법을 제공하는데
    @IdClass는 관계형 데이터베이스에 가까운 방법이고 @EmbeddedId는 좀 더 객체지향에 가까운 방법이다.
    ------------------------------------------------------------------------
    @IdClass와 @EmbeddedId는 각각 장단점이 있으므로 본인의 취향에 맞는 것을 일관성 있게 사용하면 된다.
    @EmbeddedId가 @IdClass와 비교해서 더 객체지향적이고 중복도 없어서 좋아보이긴 하지만 특정 상황에 JPQL이 조금 더 길어질 수 있다.

    ex)
        @EmbeddedId: em.createQuery("select p.id.id1, p.id.id2 from Parent p");
        @IdClass: em.createQuery("select p.id1, p.id2 from Parent p");
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
        저장 코드를 보면 식별자 클래스인 Parent1Id가 보이지 않는데, em.persist()를 호출하면서 영속성 컨텍스트에 엔티티를 등록하기 직전에 내부에서
        Parent1.id1, Parent1.id2 값을 사용해서 식별자 클래스인 Parent1Id를 생성하고 영속성 컨텍스트의 키로 사용한다.
     */
    public static void save1(EntityManager em) {
        Parent1 parent1 = new Parent1();
        parent1.setId1("myId1-1"); // 식별자
        parent1.setId2("myId1-2"); // 식별자
        parent1.setName("parentName");
        em.persist(parent1);
    }

    /*
        조회 코드를 보면 식별자 클래스인 Parent1Id를 사용해서 엔티티를 조회한다.
     */
    public static void find1(EntityManager em) {
        Parent1Id parent1Id = new Parent1Id("myId1-1", "myId1-2");
        Parent1 parent1 = em.find(Parent1.class, parent1Id);
    }

    /*
        저장하는 코드를 보면 식별자 클래스 parent2Id를 직접 생성해서 사용한다.
     */
    public static void save2(EntityManager em) {
        Parent2 parent2 = new Parent2();
        Parent2Id parent2Id = new Parent2Id("myId2-1", "myId2-2");
        parent2.setId(parent2Id);
        parent2.setName("parentName");
        em.persist(parent2);
    }

    /*
        조회 코드도 식별자 클래스 parent2Id를 직접 사용한다.
     */
    public static void find2(EntityManager em) {
        Parent2Id parent2Id = new Parent2Id("myId2-1", "myId2-2");
        Parent2 parent2 = em.find(Parent2.class, parent2Id);
    }

    /*
        이것은 순수한 자바 코드다. parent1Id와 parent2Id 인스턴스 둘 다
        myId1-1, myId1-2라는 같은 값을 가지고 있지만 인스턴스는 다르다.

        equals()를 적절히 오버라이딩했다면 참이겠지만 equals()를
        적절히 오버라이딩하지 않았다면 결과는 거짓이다.
        왜나하면 자바의 모든 클래스는 기본으로 Object 클래스를 상속받는데 이 클래스가 제공하는
        기본 equals()는 인스턴스 참조 값 비교인 == 비교(동일성 비교)를 하기 때문이다.

        영속성 컨텍스트는 엔티티의 식별자를 키로 사용해서 엔티티를 관리한다.
        그리고 식별자를 비교할 때 equals()와 hashCode()를 사용한다.
        따라서 식별자 객체의 동등성(equals 비교)이 지켜지지 않으면 예상과 다른 엔티티가 조회되거나
        엔티티를 찾을 수 없는 등 영속성 컨텍스트가 엔티티를 관리하는 데 심각한 문제가 발생한다.
        따라서 복합 키는 equals()와 hashCode()를 필수로 구현해야 한다.
        식별자 클래스는 보통 equals()와 hashCode()를 구현할 때 모든 필드를 사용한다.
     */
    public static void testEquals(EntityManager em) {
        Parent1Id parent1Id1 = new Parent1Id();
        parent1Id1.setId1("myId1-1");
        parent1Id1.setId2("myId1-2");

        Parent1Id parent1Id2 = new Parent1Id();
        parent1Id2.setId1("myId1-1");
        parent1Id2.setId2("myId1-2");

        boolean result = parent1Id1.equals(parent1Id2); // -> false
        System.out.println(result);
    }

}

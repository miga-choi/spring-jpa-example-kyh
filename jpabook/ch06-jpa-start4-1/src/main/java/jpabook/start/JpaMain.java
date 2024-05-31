package jpabook.start;

import javax.persistence.*;
import java.util.List;

/**
 * 다대다 [N:N]
 * 관계형 데이터베이스는 정규화된 테이블 2개로 다대다 관계를 표현할 수 없다.
 * 그래서 보통 다대다 관계를 일대다, 다대일 관계로 풀어내는 연결 테이블을 사용한다.
 * ------------------------------------------------------------------------
 * 예를 들어 회원들은 상품을 주문한다. 반대로 상품들은 회원들에 의해 주문된다.
 * 둘은 다대다 관계다. 따라서 회원 테이블과 상품 테이블만으로는 이 관계를 표현할 수 없다.
 * ------------------------------------------------------------------------
 * 그래서 중간에 연결 테이블을 추가해야 한다.
 * Member_product 연결 테이블을 추가해서 다대다 관계를 일대다, 다대일 관계로 풀어낼 수 있다.
 * 이 연결 테이블은 회원이 주문한 상품을 나타낸다.
 * ------------------------------------------------------------------------
 * 그런데 객체는 테이블과 다르게 객체 2개로 다대다 관계를 만들 수 있다.
 * 예를 들어회원 객체는 컬렉션을 사용해서 상품들을 참조하면 되고 반대로 상품들도 컬렉션을 사용해서 회원들을 참조하면 된다.
 * "@ManyToMany"를 사용하면 다대다 관계를 편리하게 매핑할 수 있다.
 */
public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook"); // 엔티티 매니저 팩토리 생성
        EntityManager em = emf.createEntityManager(); // 엔티티 매니저 생성
        EntityTransaction tx = em.getTransaction(); // 트랜잭션 기능 획득

        try {
            tx.begin(); // 트랜잭션 시작
            save(em);
            tx.commit(); // 트랜잭션 커밋
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); // 트랜잭션 롤백
        } finally {
            em.close(); // 엔티티 매니저 종료
        }

        emf.close(); // 엔티티 매니저 팩토리 종료
    }

    public static void save(EntityManager em) {
        Product productA = new Product();
        productA.setId("productA");
        productA.setName("상품A");
        em.persist(productA);

        Member member1 = new Member();
        member1.setId("member1");
        member1.setUsername("회원1");
        member1.getProducts().add(productA); // 연관관계 설정
        em.persist(member1);

        /*
            실행 SQL:
            INSERT INTO PRODUCT ...
            INSERT INTO MEMBER ...
            INSERT INTO MEMBER_PRODUCT ...
         */
    }

    public static void find(EntityManager em) {
        Member member = em.find(Member.class, "member1");
        List<Product> products = member.getProducts(); // 객체 그래프 탐색
        for (Product product : products) {
            System.out.println("product.name = " + product.getName());
        }

        /*
            실행 SQL:
            SELECT * FROM MEMBER_PRODUCT MP
            INNER JOIN PRODUCT P ON MP.PRODUCT_ID=P.PRODUCT_ID
            WHERE MP.MEMBER_ID=?
            ------------------------------------------------------------------------
            실행된 SQL을 보면 연결 테이블인 MEMBER_PRODUCT와 상품 테이블을 조인해서 연관된 상품을 조회한다.
            @ManyToMany 덕분에 복잡한 다대다 관계를 애플리케이션에서는 아주 단순하게 사용할 수 있다.
         */
    }

}

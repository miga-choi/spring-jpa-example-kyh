package jpabook.start;

import javax.persistence.*;

/*
    상속 관계 매핑:
    관계형 데이터베이스에는 객체지향 언어에서 다루는 상속이라는 개념이 없다.
    대신에 슈퍼타입 관계 (Super-Type Sub-Type Relationship)라는 모델링
    기법이 객체의 상속 개념과 가장 유사하다. ORM에서 이야기하는 상속 관계 매핑은
    객체의 상속 구조와 데이터베이스의 슈퍼타입 서브타입 관계를 매핑하는 것이다.
    ------------------------------------------------------------------------
    ex)
    Item:
      - id
      - 이름(name)
      - 가격(price)

    Album extends Item:
      - id
      - 이름(name)
      - 가격(price)
      - 작곡가(artist)

    Movie extends Item:
      - id
      - 이름(name)
      - 가격(price)
      - 감독(director)
      - 배우(actor)

    Book extends Item:
      - id
      - 이름(name)
      - 가격(price)
      - 작가(author)
      - ISBN(isbn)
    ------------------------------------------------------------------------
    슈퍼타입 서브타입 논리 모델을 실제 물리 모델인 테이블로 구현할 때는 3가지 방법을 선택할 수 있다.
    1. 각각의 테이블로 변환:
       각각을 모두 테이블로 만들고 조회할 때 조인을 사용한다. JPA에서는 조인 전략이라 한다.
    2. 통합 테이블로 변환:
       테이블을 하나만 사용해서 통합한다. JPA에서는 단일 테이블 전략이라 한다.
    3. 서브타입 테이블로 변환:
       서브 타입마다 하나의 테이블을 만든다. JPA에서는 구현 클래스마다 테이블 전략이라 한다.
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

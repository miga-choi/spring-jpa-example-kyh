package jpabook.start;

import javax.persistence.*;

/*
    구현 클래스마다 테이블 전략:
    구현 클래스마다 테이블 전략(Table-per-Concrete-Class Strategy)은 자식 엔티티마다
    테이블을 만든다. 그래고 자식 테이블 각각에 필요한 컬럼이 모두 있다.
    ------------------------------------------------------------------------
    InheritanceType.TABLE_PER_CLASS를 선택하면 구현 클래스마다 테이블 전략을 사용한다.
    이 전략은 자식 엔티티마다 테이블을 만든다.
    일반적으로 추천하지 않는 전략이다.
    ------------------------------------------------------------------------
    장정:
    - 서브 타입을 구분해서 처리할 때 효과적이다.
    - not null 제약조건을 사용할 수 있다.
    단점:
    - 여러 자식 테이블을 함계 조회할 때 성능이 느리다(SOL에 UNION을 사용해야 한다).
    - 자식 테이블을 통합해서 쿼리하기 어렵다.
    특징:
    - 구분 컬럼을 사용하지 않는다.
    ------------------------------------------------------------------------
    이 전략은 데이터베이스 설계자와 ORM 전문가 둘 다 추천하지 않는 전략이다.
    조인이나 단일 테이블 전략을 고려하자.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;  // 이름
    private String price; // 가격

    // Getter, Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

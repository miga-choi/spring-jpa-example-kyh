package jpabook.start;

import javax.persistence.*;

/*
    조인 전략:
    조인 전략(Joined Strategy)은 엔티티 각각을 모두 테이블로 만들고 자식 테이블이 부모 테이블의
    기본 키를 받아서 기본 키 + 외래 키로 사용하는 전략이다. 따라서 조회할 때 조인을 자주 사용한다.
    이 전략을 사용할 때 주의할 점이 있는데 객체는 타입으로 구분할 수 있지만 테이블은 타입의 개념이 없다.
    따라서 타입을 구분하는 컬럼을 추가해야 한다.
    여기서는 DTYPE 컬럼을 구분 컬럼으로 사용한다.
    ------------------------------------------------------------------------
    @Inheritance(strategy = InheritanceType.JOINED):
    상속 매핑은 부모 클래스에 @Inheritance를 사용해야 한다. 그리고 매핑 전략을 지정해야 하는데 여기서는
    조인 전략을 사용하므로 InheritanceType.JOINED를 사용했다.
    ------------------------------------------------------------------------
    @DiscriminatorColumn(name = "DTYPE"):
    부모 클래스에 구분 컬럼을 지정한다. 이 컬럼으로 저장된 지식 테이블을 구분할 수 있다.
    기본값이 DTYPE이므로 @DiscriminatorColumn()으로 줄여사용해도 된다.
    ------------------------------------------------------------------------
    조인 전략 정리:
    장점:
    - 테이블이 정규화 된다.
    - 외래 키 참조 무결성 제약조건을 활용할 수 있다.
    - 저장공간을 효율적으로 사용한다.
    단점:
    - 조회할 때 조인이 많이 사용되므로 성능이 저하될 수 있다.
    - 조회 쿼리가 복잡하다.
    - 데이터를 등록할 INSERT SQL을 두 번 실행한다.
    특징:
    - JPA 표준 명세는 구분 컬럼을 사용하도록 하지만 하이버네이트를 포함한 몇
      몇 구현체는 구분 컬럼(@DiscriminatorColumn) 없어도 동작한다.
    관련 어노테이션
    - @PrimaryKeyJoinColumn, @DiscriminatorColumn, @DiscriminatorValue
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
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

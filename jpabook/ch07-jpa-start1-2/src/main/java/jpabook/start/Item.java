package jpabook.start;

import javax.persistence.*;

/*
    단일 테이블 전략:
    단일 테이블 전략(Single-Table Strategy)은 이름 그대로 테이블을 하나만 사용한다.
    그리고 구분 컬럼(DTYPE)으로 어떤 자식 데이터가 저장되었는지 구분한다.
    조회할 때 조인을 사용하지 않으므로 일반적으로 가장 빠르다.
    ------------------------------------------------------------------------
    이 전략을 사용할 때 주의점은 자식 엔티티가 매핑한 컬럼은 모두 null을 허용해야 한다는 점이다.
    예를 들어 Book 엔티티를 저장하면 Item 테이블의 author, isbn 컬럼만 사용하고 다른 엔티티와 매핑된
    artist, director, actor 컬럼은 사용하지 않으므로 null이 입력되기 때문이다.
    ------------------------------------------------------------------------
    InheritanceType.SINGLE_TABLE로 지정하면 단일 테이블 전략을 사용한다.
    데이블 하나에 모든 것을 통합하므로 구분 컬럼을 필수로 사용해야 한다.
    단일 테이블 전략의 장단점은 하나의 테이블을 사용하는 특징과 관련있다.
    ------------------------------------------------------------------------
    @Inheritance(strategy = InheritanceType.JOINED):
    상속 매핑은 부모 클래스에 @Inheritance를 사용해야 한다. 그리고 매핑 전략을 지정해야 하는데 여기서는
    조인 전략을 사용하므로 InheritanceType.JOINED를 사용했다.
    ------------------------------------------------------------------------
    @DiscriminatorColumn(name = "DTYPE"):
    부모 클래스에 구분 컬럼을 지정한다. 이 컬럼으로 저장된 지식 테이블을 구분할 수 있다.
    기본값이 DTYPE이므로 @DiscriminatorColumn()으로 줄여사용해도 된다.
    ------------------------------------------------------------------------
    단일 테이블 전략 정리:
    장점:
    - 조인이 필요 없으므로 일반적으로 조회 성능이 빠르다.
    - 조회 쿼리가 단순하다.
    단점:
    - 자식 엔티티가 매핑한 컬럼은 모두 null을 허용해야 한다.
    - 단일 테이블에 모든 것을 저장하므로 테이블이 커질 수 있다.
      그러므로 상황에 따라서는 조회 성능이 오히려 느려질 수 있다.
    특징:
    - 구분 컬럼을 꼭 사용해야 한다. 따라서 @DiscriminatorColumn을 꼭 설정해야 한다.
    - @DiscriminatorValue를 지정하지 않으면 기본으로 엔티티 이름을 사용한다. (예 Movie, Album, Book)
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
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

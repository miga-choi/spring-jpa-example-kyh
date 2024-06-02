package jpabook.start;

import javax.persistence.*;

/**
 * 다대다: 새로운 기본 키 사용:
 * 추천하는 기본 키 생성 전략은 데이터베이스에서 자동으로 생성해주는 대리 키를 Long 값으로 사용하는 것이다.
 * 이것의 장점은 간편하고 거의 영구히 쓸 수 있으며 비즈니스에 의존하지 않는다.
 * 그리고 ORM 매핑 시에 복합 키를 만들지 않아도 되므로 간단히 매핑을 완성할 수 있다.
 * ------------------------------------------------------------------------
 * 이번에는 연결 테이블에 새로운 기본 키를 사용해보자.
 * 이 정도 되면 회원상품(MemberProduct)보다는 주문(Orders)이라는 이름이 더 어울릴 것이다
 * (ORDER는 일부 데이터베이스에 예약어로 잡혀 있어서 대신에 ORDERS를 사용하기도 한다).
 * 테이블 구조)
 * - MEMBER: MEMBER_ID (PK), USERNAME
 * - ORDERS: ORDER_ID (PK), MEMBER_ID (FK), PRODUCT_ID (FK), ORDERAMOUNT, ORDERDATE
 * - PRODUCT: PRODUCT_ID (PK), NAME
 */
@Entity
public class Orders {
    /**
     * 새로운 기본 키 ORDER_ID
     * ------------------------------------------------------------------------
     * 대리 키를 사용함으로써 이전에 보았던 식별 관계에 복합 키를 사용하는 것보다 매핑이 단순하고 이해하기 쉽다.
     */
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    /**
     * 외래 키 MEMBER_ID
     */
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    /**
     * 외래 키 PRODUCT_ID
     */
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    private int orderAmount;

    // Getter, Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }
}

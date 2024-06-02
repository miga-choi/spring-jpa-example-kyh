package jpabook.start;

import javax.persistence.*;

/**
 * 다대다: 매핑의 한계와 극복, 연결 엔티티 사용
 * ------------------------------------------------------------------------
 * "@ManyToMany"를 사용하면 연결 테이블을 자동으로 처리해주므로 도메인 모델이 단순해지고 여러 가지로 편리하다.
 * 하자만 이 매핑을 실무에서 사용하기에는 한계가 있다.
 * 예를 들어 회원이 상품을 주문하면 연결 테이블에 단순히 주문한 회원 아이디와 상품 아이디만 담고 끝나지 않는다.
 * 보통은 연결 테이블에 주문 수량 컬럼이나 주문한 날짜 같은 컬럼이 더 필요하다.
 * ------------------------------------------------------------------------
 * ex)
 * 테이블 관계:
 * * Member -[1:N]- Member_Product -[N:1]- Product
 * - Member: MEMBER_ID (PK), USERNAME
 * - Member_Product: MEMBER_ID (PK, FK), PRODUCT_ID (PK, FK), ORDERAMOUNT
 * - Product: PRODUCT_ID (PK), NAME
 * 객체 관계:
 * * Member - [N:N] - Product
 * 결론)
 * 위 예제 처럼 테이블에 "주문 수량 (ORDERAMOUNT)" 컬럼을 추가하면 더는 @ManyToMany를 사용할 수 없다.
 * 이유는 주문 엔티티나 상품 엔티티에는 추가한 컬럼들을 매핑할 수 없기 때문이다.
 * 그래서 위와 같은 상황을 해결 하려면 결국 연결 테이블을 매핑하는 연결 엔티티를 만들고 이곳에 추가한 컬럼들을 매핑해야 한다.
 * 그리고 엔티티 간의 관계도 테이블 관계처럼 다대다에서 일대다, 다대일 관계로 풀어야 한다.
 */
@Entity
@IdClass(MemberProductId.class)
public class MemberProduct {
    @Id
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member; // MemberProductId.member와 연결

    @Id
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product; // MemberProductId.product와 연결

    private int orderAmount;
}

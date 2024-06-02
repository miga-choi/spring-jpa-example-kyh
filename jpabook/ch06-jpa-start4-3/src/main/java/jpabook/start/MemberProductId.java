package jpabook.start;

import java.io.Serializable;

// 회원상품 식별자 클래스
public class MemberProductId implements Serializable {
    private String member;  // MemberProduct.member와 연결
    private String product; // MemberProduct.product와 연결

    // hashCode an equals
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    // Getter, Setter
    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}

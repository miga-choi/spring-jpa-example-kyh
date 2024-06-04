package jpabook.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 주문
 * ------------------------------------------------------------------------
 * 주문(Order) - [N:1] - 회원(Member)
 * 주문(Order) - [1:N] - 상품(Item)
 * 주문(Order) - [1:1] - 배송(Delivery)
 */
@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    private Date orderDate; // 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member; // 주문 회원

    @OneToMany(mappedBy = "order")
    private List<Item> items = new ArrayList<Item>();

    @OneToOne
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery; // 배송 정보

    // Getter, Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Member getMember() {
        return member;
    }

    // == 연관관계 메소드 == //
    public void setMember(Member member) {
        // 기존 관계 제거
        if (this.member != null) {
            this.member.getOrders().remove(this);
        }
        this.member = member;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
}

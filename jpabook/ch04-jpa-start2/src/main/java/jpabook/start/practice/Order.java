package jpabook.start.practice;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @Column(name = "MEMBER_ID")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;           // 주문 날짜

    @Enumerated(EnumType.STRING)
    private enums.OrderStatus status; // 주문 상태

    // Getter, Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public enums.OrderStatus getStatus() {
        return status;
    }

    public void setStatus(enums.OrderStatus status) {
        this.status = status;
    }
}

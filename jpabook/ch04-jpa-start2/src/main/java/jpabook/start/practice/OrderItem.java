package jpabook.start.practice;

import javax.persistence.Id;

public class OrderItem {
    @Id
    private Long id;

    private Long orderId;

    private Long itemId;

    private int orderPrice;

    private int count;
}

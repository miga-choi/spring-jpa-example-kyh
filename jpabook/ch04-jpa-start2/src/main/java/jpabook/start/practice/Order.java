package jpabook.start.practice;

import javax.persistence.Id;

public class Order {
    @Id
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;
}

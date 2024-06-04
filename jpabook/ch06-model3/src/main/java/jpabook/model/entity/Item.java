package jpabook.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 주문 상품
 */
@Entity
public class Item {
    @Id
    @GeneratedValue
    @Column()
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
}

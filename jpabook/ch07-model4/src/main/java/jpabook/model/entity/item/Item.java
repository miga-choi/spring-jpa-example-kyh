// Item.java
package jpabook.model.entity.item;

import jpabook.model.entity.Category;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by holyeye on 2014. 3. 11..
 */

@Entity
/*
    상속 관계를 매핑하기 위해 부모 클래스인 Item에 @Inheritance 어노테이션을 사용하고
    strategy 속성에 InheritanceType.SINGLE_TABLE을 선택해서 단일 테이블 전략을 선택했다.
 */
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
/*
    단일 테이블 전략은 구분 컬럼을 필수로 사용해야 한다. @DiscriminatorColumn 어노테이션을 사용하고 name
    속성에 DTYPE이라는 구분 컬럼으로 사용할 이름을 주었다.참고로 생략하면 DTYPE이라는 이름을 기본으로 사용한다.
 */
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;        // 이름
    private int price;          // 가격
    private int stockQuantity;  // 재고수량

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Item { " +
                "id=" + id +
                ", name=\"" + name + "\"" +
                ", price=" + price +
                " }";
    }
}

package jpabook.start;

import javax.persistence.Entity;

@Entity
public class Seller extends BaseEntity {
    // id 상속
    // name 상속
    private String shopName;

    // Getter, Setter
    // getId 상속
    // setId 상속
    // getName 상속
    // setName 상속

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}

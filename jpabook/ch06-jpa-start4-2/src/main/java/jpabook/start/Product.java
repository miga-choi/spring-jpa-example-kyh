package jpabook.start;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "PRODUCT_ID")
    private String id;

    private String name;

    /**
     * 다대다: 양방향
     * ------------------------------------------------------------------------
     * 다대다 매핑이므로 역방향(Product -> Member)도 @ManyToMany를 사용했다.
     * 그리고 양쪽 중 원하는 곳에 mappedBy로 연관관계의 주인을 지정한다(물론 mappedBy가 없는 곳이 연관관계의 주인이다).
     */
    @ManyToMany(mappedBy = "products") // 역방향 추가
    private List<Member> members;

    // Getter, Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}

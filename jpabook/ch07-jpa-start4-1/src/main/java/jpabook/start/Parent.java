package jpabook.start;

import javax.persistence.*;

// 부모
@Entity
public class Parent {
    @Id
    @GeneratedValue
    @Column(name = "PARENT_ID")
    private Long id;

    private String name;

    /*
        @JoinColumn 대신에 @JoinTable을 사용했다.
        ------------------------------------------------------------------------
        @JoinTable:
        - name: 매핑할 조인 테이블 이름
        - joinColumns: 현재 엔티티를 참조하는 외래 키
        - inverseJoinColumns: 반대방향 엔티티를 참조하는 외래 키
     */
    @OneToOne
    @JoinTable(name = "PARENT_CHILD",
            joinColumns = @JoinColumn(name = "PARENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CHILD_ID")
    )
    private Child child;

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

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }
}

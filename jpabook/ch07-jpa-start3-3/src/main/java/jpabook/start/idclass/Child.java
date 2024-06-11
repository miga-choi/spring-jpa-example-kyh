package jpabook.start.idclass;

import javax.persistence.*;

// 자식
@Entity
@IdClass(ChildId.class)
public class Child {
    /*
        식별 관계는 기본 키와 외래 키를 같이 매핑해야 한다.
        따라서 식별자 매핑인 @Id와 연관관계 매핑인 @ManyToOne을 같이 사용하면 된다.

        @Id로 기본 키를 매핑하면서 @ManyToOne과 @JoinColumn으로 외래 키를 매핑한다.
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    public Parent parent;

    @Id
    @Column(name = "CHILD_ID")
    private String childId;

    private String name;

    // Getter, Setter
    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

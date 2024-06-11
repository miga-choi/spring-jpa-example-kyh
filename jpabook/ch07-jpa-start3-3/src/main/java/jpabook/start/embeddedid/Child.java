package jpabook.start.embeddedid;

import javax.persistence.*;

// 자식
@Entity
public class Child {
    @EmbeddedId
    private ChildId id;

    /*
        @EmbeddedId는 식별 관계로 사용할 연관관계의 속성에 @MapsId를 사용하면 된다.

        @IdClass와 다른 점은 @Id 대신에 @MapsId를 사용한 점이다.
        @MapsId는 외래 키와 매핑한 연관관계를 기본 키에도 매핑하겠다는 뜻이다.
        @MapsId의 속성 값은 @EmbeddedId를 사용한 식별자 클래스의 기본 키 필드를 지정하면 된다.
     */
    @MapsId("parentId") // ChildId.parentId 매핑
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;

    private String name;

    // Getter, Setter
    public ChildId getId() {
        return id;
    }

    public void setId(ChildId id) {
        this.id = id;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

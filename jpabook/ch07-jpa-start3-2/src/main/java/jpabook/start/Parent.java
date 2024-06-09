package jpabook.start;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
/*
    @IdClass:
        복합 키 테이블은 비식별 관계고 PARENT는 복합 기본 키를 사용한다.
        참고로 여기서 이야기하는 부모(PARENT) 자식(CHILD)은 객체의 상속과는 무관하다.
        단지 테이블의 키를 내려받은 것을 강조하려고 이름을 이렇게 지었다.
    ------------------------------------------------------------------------
    ex)
        PARENT:
            - PARENT_ID1 (PK)
            - PARENT_ID2 (PK)
            - NAME
        CHILD:
            - CHILD_ID (PK)
            - PARENT_ID1 (FK)
            - PARENT_ID2 (FK)
            - NAME
    설명)
        PARENT 테이블을 보면 기본 키를 PARENT_ID1, PARENT_ID2로 묶은 복합 키로 구성했다.
        따라서 복합 키를 매핑하기 위해 식별자 클래스를 별도로 만들어야 한다.
    ------------------------------------------------------------------------
    @IdClass를 사용할 때 식별자 클래스는 다음 조거을 만족해야 한다.
        - 식별자 클래스의 속성명과 엔티티에서 사용하는 식별자의 속성명이 같아야 한다.
          예제의 Parent.id1과 Parent.id1, 그리고 Parent.id2와 ParentId.id2가 같다.
        - Serializeable 인터페이스를 구현해야 한다.
        - equals, hashCode를 구현해야 한다.
        - 기본 생성자가 있어야 한다.
        - 식별자 클래스는 public이어야 한다.
 */
@IdClass(ParentId.class)
public class Parent {
    @Id
    @Column(name = "PARENT_ID1")
    private String id1; // ParentId.id1과 연결

    @Id
    @Column(name = "PARENT_ID2")
    private String id2; // ParentId.id2과 연결

    private String name;

    // Getter, Setter
    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

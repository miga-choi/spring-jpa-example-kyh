package jpabook.start;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
/*
    Parent2 엔티티에서 식별자 클래스를 직접 사용하고 @EmbeddedId 어노테이션을 적어주면 된다.

    @IdClass가 데이터베이스에 맞춘 방법이라면 @EmbeddedId는 좀 더 객체지향적인 방법이다.

    @IdClass와는 다르게 @EmbeddedId를 적용한 식별자 클래스는 식별자 클래스에 기본 키를 직접 매핑한다.

    @EmbeddedId를 적용한 식별자 클래스는 다음 조건을 만족해야 한다.
        - @Embeddable 어노테이션을 붙여주어야 한다.
        - Serializable 인터페이스를 구현해야 한다.
        - equals, hashCode를 구현해야 한다.
        - 기본 생성자가 있어야 한다.
        - 식별자 클래스는 public이어야 한다.
 */
public class Parent2 {
    @EmbeddedId
    private Parent2Id id;

    private String name;

    // Getter, Setter
    public Parent2Id getId() {
        return id;
    }

    public void setId(Parent2Id id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

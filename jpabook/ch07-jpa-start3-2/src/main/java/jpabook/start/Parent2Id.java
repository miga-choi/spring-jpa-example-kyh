package jpabook.start;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/*
    Parent 엔티티에서 식별자 클래스를 직접 사용하고 @EmbeddedId 어노테이션을 적어주면 된다.
 */
@Embeddable
public class Parent2Id implements Serializable {
    @Column(name = "PARENT2_ID1")
    private String id1;

    @Column(name = "PARENT2_ID2")
    private String id2;

    public Parent2Id() {
    }

    public Parent2Id(String id1, String id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

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

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

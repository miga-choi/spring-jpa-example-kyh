package jpabook.start;

import java.io.Serializable;

/*
    복합 키와 equals(), hashCode()
    복합 키는 equals()와 hashCode()를 필수로 구현해야 한다.
 */
public class Parent1Id implements Serializable {
    private String id1; // Parent1.id1 매핑
    private String id2; // Parent1.id2 매핑

    public Parent1Id() {
    }

    public Parent1Id(String id1, String id2) {
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

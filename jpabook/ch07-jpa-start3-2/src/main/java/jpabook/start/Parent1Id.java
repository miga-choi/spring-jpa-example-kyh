package jpabook.start;

import java.io.Serializable;

public class Parent1Id implements Serializable {
    private String id1; // Parent1.id1 매핑
    private String id2; // Parent1.id2 매핑

    public Parent1Id() {
    }

    public Parent1Id(String id1, String id2) {
        this.id1 = id1;
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

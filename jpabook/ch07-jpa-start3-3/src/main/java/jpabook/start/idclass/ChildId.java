package jpabook.start.idclass;

import java.io.Serializable;

// 자식 ID
public class ChildId implements Serializable {
    private String parent;  // Child.parent 매핑
    private String childId; // Child.childId 매핑

    // equals, hashCode
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

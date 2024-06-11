package jpabook.start.idclass;

import java.io.Serializable;

// 손자 ID
public class GrandChildId implements Serializable {
    private ChildId child; // GrandChild.child 매핑
    private String id;     // GrandChild.id 매핑

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

package jpabook.start;

import javax.persistence.*;

@Entity
/*
    부모 테이블의 기본 키 컬럼이 복합 키이므로 자식 테이블의 외래 키도 복합 키다.
    따라서 외래 키 매핑 시 여러 컬럼을 매핑해야 하므로 @JoinColumns 어노테이션을
    사용하고 각각의 외래 키 컬럼을 @JoinColumn으로 매핑한다.

    참고로 예제처럼 @JoinColumn의 name 속성과 referencedColumnName 속성의
    값이 같으면 referencedColumn은 생략해도 된다.
 */
public class Child2 {
    @Id
    private String id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PARENT2_ID1", referencedColumnName = "PARENT2_ID1"),
            @JoinColumn(name = "PARENT2_ID2", referencedColumnName = "PARENT2_ID2")
    })
    private Parent1 parent1;

    // Getter, Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Parent1 getParent() {
        return parent1;
    }

    public void setParent(Parent1 parent1) {
        this.parent1 = parent1;
    }
}

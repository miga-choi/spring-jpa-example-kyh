package jpabook.start;

import javax.persistence.*;

// 자식
@Entity
public class Child {
    @Id
    @GeneratedValue
    @Column(name = "CHILD_ID")
    private Long id;

    private String name;

    @ManyToOne(optional = false)
    @JoinTable(name = "PARENT_CHILD",
            joinColumns = @JoinColumn(name = "CHILD_ID"),
            inverseJoinColumns = @JoinColumn(name = "PARENT_ID")
    )
    private Parent parent;

    // Getter, Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }
}

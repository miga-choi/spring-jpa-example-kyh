package jpabook.start;

import javax.persistence.*;

@Entity
public class Locker {
    @Id
    @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    private String name;

    /**
     * 양방향이므로 연관관계의 주인을 정해야 한다.
     * MEMBER 테이블이 외래 키를 가지고 있으므로 Member 엔티티에 있는 Member.locker가 연관관계의 주인이다.
     * 따라서 반대 매핑인 사물함의 Locker.member는 mappedBy를 선언해서 연관관계의 주인이 아니라고 설정했다.
     */
    @OneToOne(mappedBy = "locker")
    private Member member;

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
}

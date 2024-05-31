package jpabook.start;

import javax.persistence.*;

/**
 * 대상 테이블에 외래 키
 * ------------------------------------------------------------------------
 * 단방향
 * 일대일 관계 중 대상 테이블에 외래 키가 있는 단방향 관계는 JPA에서 지원하지 않는다.
 * 그리고 이런 모양으로 매핑할 수 있는 방법도 없다.
 * 이때는 단방향 관계를 Locker에서 Member 방향으로 수정하거나,
 * 양방향 관계로 만들고 Locker를 연관관계의 주인으로 설정해야 한다.
 * ------------------------------------------------------------------------
 * 참고로 JPA2.0부터 일대다 단방향 관계에서 대상 테이블에 외래 키가 있는 매핑을 허용했다.
 * 하지만 일대일 단방향은 이런 매핑을 허용하지 않는다.
 */
@Entity
public class Locker {
    @Id
    @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}

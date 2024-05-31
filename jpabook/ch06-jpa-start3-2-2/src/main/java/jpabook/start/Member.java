package jpabook.start;

import javax.persistence.*;

/**
 * 대상 테이블에 외래 키
 * ------------------------------------------------------------------------
 * 양방향
 * 일대일 매핑에서 대상 테이블에 외래 키를 두고 싶으면 이렇게 양방향으로 매핑한다.
 * 주 엔티티인 Member 엔티티 대신에 대상 엔티티인 Locker를 연관관계의
 * 주인으로 만들어서 LOCKER 테이블의 외래 키를 관리하도록 했다.
 */
@Entity
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    @OneToOne(mappedBy = "member")
    private Locker locker;

    // Getter, Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }
}

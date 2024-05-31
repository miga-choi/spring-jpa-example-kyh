package jpabook.start;

import javax.persistence.*;

/**
 * 주 테이블에 외래 키
 * 일대일 관계를 구성할 때 객체지향 개발자들은 주 테이블에 외래 키가 있는 것을 선호한다.
 * JPA도 주 테이블에 외래 키가 있으면 좀 더 편리하게 매핑할 수 있다.
 * ------------------------------------------------------------------------
 * 양방향
 */
@Entity
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    /**
     * 일대일 관계이므로 객체 매핑에 @OneToOne을 사용했고 데이터베이스에는
     * LOCKER_ID 외래 키에 유니크(UNIQUE) 제약 조건을 추가 했다.
     * 참고로 이 관계는 다대일 단방향(@ManyToOne)과 거의 비슷하다.
     */
    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
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

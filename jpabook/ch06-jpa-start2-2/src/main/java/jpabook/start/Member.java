package jpabook.start;

import javax.persistence.*;

@Entity
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    /**
     * 일대다 단방향 매핑 반대편에 다대일 단방향 매핑을 추가했다.
     * 이때 일대다 단방향 매핑과 같은 TEAM_ID 외래 키 컬럼을 매핑했다.
     * 이렇게 되면 둘 다 같은 키를 관리하므로 문제가 발생할 수 있다.
     * 따라서 반대편인 다대일 쪽은 insertable = false,
     * updatable = false로 설정해서 읽기만 가능하게 했다.
     * ------------------------------------------------------------------------
     * 이 방법은 일대다 양방향 매핑이라기보다는 일대다 단방향 매핑 반대편에 다대일
     * 단방향 매핑을 읽기 전용으로 추가해서 일대다 양방향처럼 보이도록 하는 방법이다.
     * 따라서 일대다 단방향 매핑이 가지는 단점을 그대로 가진다. 될 수 있으면 다대일
     * 양방향 매핑을 사용하자.
     */
    @ManyToOne
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
    private Team team;

    public Member() {
    }

    public Member(String username) {
        this.username = username;
    }

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}

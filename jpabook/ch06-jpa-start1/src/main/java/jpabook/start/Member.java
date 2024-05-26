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
     * 다대일 단방향 [N:1]
     * 회원은 Member.team으로 팀 엔티티를 참조할 수 있지만 반대로 팀에는 회원을 참조하는 필드가 없다.
     * 따라서 회원과 팀은 다대일 단방향 연관관계다.
     *
     * @JoinColumn(name = "TEAM_ID")를 사용해서 Member.team 필드를 TEAM_ID 외래 키와 매핑했다.
     * 따라서 Member.team 필드로 회원 테이블의 TEAM_ID 외래 키를 관리한다.
     */
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

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

        // 무한루프에 빠지지 않도록 체크
        if (!team.getMembers().contains(this)) {
            team.getMembers().add(this);
        }
    }
}

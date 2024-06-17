package jpabook.start;

import javax.persistence.*;

@Entity
public class Member {
    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    @ManyToOne
    private Team team;

    // Getter, Setter
    public String getUsername() {
        return username;
    }

    public Team getTeam() {
        return team;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}

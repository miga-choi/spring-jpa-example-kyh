package jpabook.start;

import javax.persistence.*;

@Entity
public class Member {
    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String name;

    @ManyToOne
    private Team team;

    // Getter, Setter
    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}

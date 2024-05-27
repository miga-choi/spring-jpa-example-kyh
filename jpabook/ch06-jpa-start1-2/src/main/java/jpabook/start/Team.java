package jpabook.start;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    /**
     * 다대일 양방향 [N:1, 1:N]
     * 1. 양방향은 외래 키가 있는 쪽이 연관관계의 주인이다.
     * 일대다와 다대일 연관관계는 항상 다(N)에 외래 키가 있다. 여기서는 다쪽인 MEMBER 테이블이 외래키를 가지고 있으므로 Member.team이 연관관계의 주인이다.
     * JPA는 외래 키를 관리할 때 연관관계의 주인만 사용한다. 주인이 아닌 Team.members는 조회를 위한 JPQL이나 객체 그래프를 탐색할 때 사용한다.
     * 2. 양방향 연관관계는 항상 서로를 참조해야 한다.
     * 양방향 연관관계는 항상 서로 참조해야 한다. 어느 한 쪽만 참조하면 양방향 연관관계가 성립하지 않는다. 항상 서로 참조하게 하려면 연관관계 편의 메소드를
     * 작성하는 것이 좋은데 회원의 setTeam(), 팀의 addMember() 메소드가 이런 편의 메소드들이다. 편의 메소드는 한 곳에만 작성하거나 양쪽 다 작성할 수
     * 있는 메소드를 양쪽에 다 작성해서 둘 중 하나만 호출하면 된다. 또한 무한루프에 빠지지 않도록 검사하는 로직도 있다.
     */
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<Member>();

    public void addMember(Member member) {
        this.members.add(member);

        if (member.getTeam() != this) {
            // 무한루프에 빠지지 않도록 체크
            member.setTeam(this);
        }
    }

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

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}

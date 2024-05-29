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
     * 일대다
     * 일대다 관계는 다대일 관계의 반대 방향이다. 일대다 관계는 엔티티를 하나 이상 참조할 수
     * 있으므로 자바 컬렉션인 Collection, List, Set, Map 중에 하나를 사용해야 한다.
     * ------------------------------------------------------------------------
     * 일대다 단방향 [1:N]
     * 하나의 팀은 여러 회원을 참조할 수 있는데 이런 관계를 일대다 관계라 한다.
     * 그리고 팀은 회원들을 참조하지만 반대로 회원은 팀을 참조하지 안흐면 둘의 관계는 단방향이다.
     * ------------------------------------------------------------------------
     * 일대다 단방향 관계는 약간 특이한데, 예를들어 팀엔티티의 Team.members로 회원 테이블의 TEAM_ID 외래 키를 관리한다.
     * 보통 자신이 매핑한 테이블의 외래 킬를 관리하는데, 이 매핑은 반대쪽 테이블에 있는 외래 키를 관리한다.
     * 그럴 수밖에 없는 것이 일대다 관계에서 외래 키는 항상 다쪽 테이블에 있다.
     * 하지만 다 쪽인 Member 엔티티에는 외래 키를 매핑할 수 있는 참조 필드가 없다.
     * 대신에 반대쪽인 Team 엔티티에만 참조 필드인 members가 있다.
     * 따라서 반대편 테이블의 외래 키를 관리하는 특이한 모습이 나타난다.
     * ------------------------------------------------------------------------
     * 일대다 단방향 관계를 매핑할 때는 @JoinColumn을 명시해야 한다.
     * 그렇지 않으면 JPA는 연결 테이블을 중간에 두고 연관관계를 관리하는 조인 테이블 (Join Table) 전략을 기본으로 사용해서 매핑한다.
     * ------------------------------------------------------------------------
     * 일대다 단방향 매핑의 단점:
     * 일대다 단방향 매핑의 단점은 매핑한 객체가 관리하는 외래 키가 다른 테이블에 있다는 점이다.
     * 본인 테이블에 외래 키가 있으면 엔티티의 저장과 연관관계 처리를 INSERT SQL 한 번으로 끝낼 수 있지만,
     * 다른 테이블에 외래 키가 있으면 연관관계 처리를 위한 UPDATE SQL을 추가로 실행해야 한다.
     */
    @OneToMany
    @JoinColumn(name = "TEAM_ID") // Member 테이블의 TEAM_ID (FK)
    private List<Member> members = new ArrayList<Member>();

    public Team() {
    }

    public Team(String name) {
        this.name = name;
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

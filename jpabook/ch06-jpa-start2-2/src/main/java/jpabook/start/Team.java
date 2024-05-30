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
     * 일대다 양방향 [1:N, N:1]
     * 일대다 양방향 매핑은 존재하지 않는다. 대신 다대일 양방향 매핑을 사용해야 한다 (일대다 양방향과 다대일 양방향은 사실 똑같은 말이다.
     * 여기서는 왼쪽을 연관관계의 주인으로 가정해서 분류했다. 예를 들어 다대일이면 다(N)가 연관관계의 주인이다).
     * ------------------------------------------------------------------------
     * 더 정확히 말하자면 양방향 매핑에서 @OneToMany는 연관관계의 주인이 될 수 없다.
     * 왜나하면 관계형 데이터베이스의 특성상 일대다, 다대일 관계는 항상 다 쪽에 외래 키가 있다.
     * 따라서 @OneToMany, @ManyToOne 둘 중에 연관관계의 주인은 항상 다 쪽인 @ManyToOne을 사용한 곳이다.
     * 이런 이유로 @ManyToOne에는 mappedBy 속성이 없다.
     * ------------------------------------------------------------------------
     * 그렇다고 일대다 양방향 매핑이 완전히 불가능한 것은 아니다.
     * 일대다 단방향 매핑 반대편에 같은 외래 키를 사용하는 다대일 단방향 매핑을 읽기 전용으로 하나 추가하면 된다.
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

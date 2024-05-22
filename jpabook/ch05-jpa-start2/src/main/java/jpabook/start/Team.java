package jpabook.start;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id
    @Column(name = "TEAM_ID")
    private String id;

    private String name;

    /**
     * 양방향 연관관계 매핑
     * 팀과 회원은 일대다 관계다. 따라서 팀 엔티티에 컬렉션인 List<Member> members를 추가했다.
     * 그리고 일대다 관계를 매핑하기 위해 @OneToMany 매핑 정보를 사용했다.
     * mappedBy 속성은 양방향 매핑일 때 사용하는데 반대쪽 매핑의 필드 이름을 값으로 주면 된다.
     * 반대쪽 매핑이 Member.team이므로 team을 값으로 주었다.
     * ------------------------------------------------------------------------
     * 엄밀히 이야기하면 객체에는 양방향 연관관계라는 것이 없다. 서로 다른 단방향 연관관계 2개를
     * 애플리케이션 로직으로 잘 묶어서 양방향인 것처럼 보이게 할 뿐이다. 반면에 데이터베이스 테이블은
     * 외래 키 하나로 양쪽이 서로 조인할 수 있다. 따라서 테이블은 외래 키 하나만으로 양방향 연관관계를 맺는다.
     * ------------------------------------------------------------------------
     * 객체 연관관계는 다음과 같다.
     * - 회원 -> 팀 연관관계 1개 (단방향)
     * - 팀 -> 회원 연관관계 1개 (단방향)
     * 테이블 연관관계는 다음과 같다.
     * - 회원 <-> 팀 연관관계 1개 (양방향)
     * ------------------------------------------------------------------------
     * 테이블은 외래 키 하나로 두 테이블의 연관관계를 관리한다.
     * 엔티티를 단방향으로 매핑하면 참조를 하나만 사용하므로 이 참조로 외래 키를 관리하면 된다.
     * 그런데 엔티티를 양방향으로 매핑하면 회원 -> 팀, 팀 -> 회원 두 곳에서 서로를 참조한다.
     * 따라서 객체의 연관관계를 관리하는 포인트는 2곳으로 늘어난다.
     * 엔티티를 양방향 연관관계로 설정하면 객체의 참조는 둘인데 외래 키는 하나다.
     * 따라서 둘 사이에 차이가 발생한다.
     * 이런 차이로 인해 JPA에서는 두 객체 연관관계 중 하나를 정해서 테이블의 외래 키를 관리해야 하는데
     * 이것을 연관관계의 주인(Owner)이라 한다.
     */
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<Member>();

    public Team() {
    }

    public Team(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter, Setter

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

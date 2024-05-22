package jpabook.start;

import javax.persistence.*;

/**
 * 연관관계 매핑 핵심 키워드:
 * 1. 방향(Direction): [단방향, 양방향]이 있다.
 * -  예를 들어 회원과 팀이 관계가 있을 때 회원 -> 팀 또는 팀 -> 회원 둘 중 한 쪽만 참조하는 것을 단방향 관계라 하고,
 * -  회원 -> 팀, 팀 -> 회원 양쪽 모두 서로 참조하는 것을 양방향 관계라 한다.
 * -  방향은 객체관계에만 존재하고 테이블 관계는 항상 양방향이다.
 * 2. 다중성(Multiplicity): [다대일(N:1), 일대다(1:N), 일대일(1:1), 다대다(N:M)] 다중성이 있다.
 * -  예를 들어 회원과 팀이 관계가 있을 때 여러 회원은 한 팀에 속하므로 회원과 팀은 다대일 관계다.
 * -  반대로 한 팀에 여러 회원이 소속될 수 있으므로 팀과 회원은 일대다 관계다.
 * 3. 연관관계의 주인(owner):
 * -  객체를 양방향 연관관계로 만들면 연관관계의 주인을 정해야 한다.
 * ------------------------------------------------------------------------------------------------------------
 * 단방향 연관관계:
 */

@Entity
public class Member {
    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    // 연관관계 매핑
    /**
     * 객체 연관관계: 회원 객체의 Member.team 필드 사용
     * 테이블 연관관계: 회원 테이블의 MEMBET.TEAM_ID 외래 키 컬럼을 사용
     * ------------------------------------------------------------------------------------------------
     * ManyToOne
     * - 이름 그대로 다대이(N:1) 관계라는 매핑 정보다.
     * - 회원과 팀은 다대일 관계다.
     * - 연관관계를 매핑할 때 이렇게 다중성을 나타내는 어노테이션을 필수로 사용해야 한다.
     * optional:
     * - false로 설정하면 연관된 엔티티가 항상 있어야 한다. 기본값: true.
     * fetch:
     * - 글로벌 페치 전략을 설정한다. 기본값: @ManyToOne=FetchType.EAGER, @OneToMany=FetchType.LAZY
     * cascade:
     * - 영속성 전이 기능을 사용한다.
     * targetEntity:
     * - 연관된 엔티티의 타입 정보를 설정한다. 이 기능은 거의 사용하지 않는다. 컬럭션을 사용해도 제네릭으로 타입 정보를 알 수 있다.
     * ------------------------------------------------------------------------------------------------
     * JoinColumn(name = "TEAM_ID")
     * - 조인 컬럼은 외래 키를 매핑할 때 사용한다.
     * - name 속성에는 매핑할 외래 키 이름을 지정한다.
     * - 회원과 팀 테이블은 TEAM_ID 외래 키로 연관관계를 맺으므로 이 값을 지정하면 된다.
     * - 이 어노테이션은 생략할 수 있다.
     * P.S.: @JoinColumn 생략:
     * - @JoinColumn을 생략하면 외래 키를 찾을 때 기본 전략을 사용한다.
     * - 기본전략: 필드명 + _ + 참조하는 외래 키를 찾을 때 기본 전략을 사용한다.
     * name:
     * - 매핑할 외래 키 이름. 기본값: 필드명 + _ + 참조하는 테이블의 기본 키 컬럼명.
     * referencedColumnName:
     * - 외래 키가 참조하는 대상 테이블의 컬럼명. 기본값: 참조하는 테이블의 기본 키 컬럼명.
     * foreignKey:
     * - (DDL) 외래 키 제약조건을 직접 지정할 수 있다. 이 속성은 테이블을 생성할 때만 사용한다.
     * unique, nullable, insertable, updatable, columnDefinition, table:
     * - @Column의 속성과 같다.
     * ------------------------------------------------------------------------
     * 양방향 연관관계:
     * 연관관계의 주인을 정한다는 것은 사실 외래 키 관리자를 선택하는 것이다.
     * 여기서는 회원 테이블에 있는 TEAM_ID 외래 키를 관리할 관리자를 선택해야 한다.
     * 만약 회원 엔티티에 있는 Member.team을 주인으로 선택하면 자기 테이블에 있는 회래 키를 관리하면 된다.
     * 하지만 팀 엔티티에 있는 Team.members를 주인으로 선택하면 물리적으로 전혀 다른 테이블의 외래 키를 관리해야 한다.
     * 왜냐하면 이 경우 Team.members가 있는 Team 엔티티는 TEAM 테이블에 매피되어 있는데 관리해야할 외래 키는 MEMBER 테이블에 있기 때문이다.
     * ------------------------------------------------------------------------
     * 연관관계의 주인은 외래 키가 있는 곳:
     * 연관관계의 주인은 테이블에 외래 키가 있는 곳으로 정해야한다. 여기서는 회원 테이블이 외래 키를 가지고 있으므로 Member.team이 주인이 된다.
     * 주인이 아닌 Team.members에는 mappedBy="team" 속성을 사용해서 주인이 아님을 설정한다.
     * 그리고 mappedBy 속성의 값으로는 연관관계의 주인인 team을 주면 된다.
     * 여기서 mappedBy의 값으로 사용된 team은 연관관계의 주인인 Member 엔티티의 team 필드를 말한다.
     */
    // 회원 -> 팀(Member.team) 방향
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Member() {
    }

    public Member(String id, String username) {
        this.id = id;
        this.username = username;
    }

    // Getter, Setter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return this.team;
    }

    // 연관관계 설정
    public void setTemm(Team temm) {
        this.team = temm;
    }
}

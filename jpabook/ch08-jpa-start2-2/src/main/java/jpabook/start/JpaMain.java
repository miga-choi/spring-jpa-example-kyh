package jpabook.start;

import javax.persistence.*;

/*
    즉시 로딩과 지연 로딩
    ------------------------------------------------------------------------------------------------------------
    프록시 객체는 주로 연관된 엔티티를 지연 로딩할 때 사용한다.

    예제1) merber1이 team1에 소속해 있다고 가정해보자.
        Member member = em.find(Member.class, "member1");
        Team team = member.getTeam(); // 갹채 그래프 탐색
        System.out.println(team.getName()); // 팀 엔티티 사용
    분석)
        회원 엔티티를 조회할 때 연관된 팀 엔티티도 함께 데이터베이스에서 조회하는 것을 좋을까?
        아니면 회원 엔티티만 조회해 두고 팀 엔티티는 실제 사용하는 시점에 데이터베이스에서 조회하는 것이 좋을까?

    JPA는 개발자가 연관된 엔티티의 조회 시점을 선택할 수 있도록 다음 두 가지 방법을 제공한다.

    * 즉시 로딩: 엔티티를 조회할 때 연관된 엔티티도 함께 조회한다.
      - 예: em.find(Member.class, "member1")를 호출할 때 회원 엔티티와 연관된 팀 엔티티도 함께 조회한다.
      - 설정 방법: @ManyToOne(fetch = FetchType.EAGER)

    * 지연 로딩: 연관된 엔티티를 실제 사용할 때 조회한다.
      - 예: member.getTeam().getName() 처럼 조회한 팀 엔티티를 실제 사용하는 시점에 JPA가 SQL을 호출해서 팀 엔티티를 조회한다.
      - 설정 방법: @ManyToOne(fetch = FetchType.LAZY)

    ------------------------------------------------------------------------------------------------------------
    즉시 로딩
    즉시 로딩 (EAGER LOADING)을 사용하려면 @ManyToOne의 fetch 속성을 FetchType.EAGER로 지정한다.
    ------------------------------------------------------------------------------------------------------------
    지연 로딩
    지연 로딩 (LAZY LOADING)을 사용하려면 @ManyToOne의 fetch 속성을 FetchType.LAZY로 지정한다.
 */
public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook"); // 엔티티 매니저 팩토리 생성
        EntityManager em = emf.createEntityManager(); // 엔티티 매니저 생성
        EntityTransaction tx = em.getTransaction(); // 트랜잭션 기능 획득

        try {
            tx.begin(); // 트랜잭션 시작
            tx.commit(); // 트랜잭션 커밋
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); // 트랜잭션 롤백
        } finally {
            em.close(); // 엔티티 매니저 종료
        }

        emf.close(); // 엔티티 매니저 팩토리 종료
    }

    /*
        예제1) 즉시 로딩, 회원을 조회할 때 팀 즉시 로딩
            1. 로딩 -> member1
            2. 즉시로딩 -> team1 (실제 team1 엔티티)

        회원과 팀을 즉시 로딩으로 설정했다. 따라서 em.find(Member.class, "member1)로 회원을 조회하는 순간 팀도 함께
        조회한다. 이때 회원과 팀 두 테이블을 조회해야 하므로 쿼리를 ㅈ번 실행할 것 같지만, 대부분의 JPA 구현체는 즉시 로딩을
        최적화하기 위해 가능하면 조인 쿼리를 사용한다. 여기서는 회원과 팀을 조인해서 쿼리 한 번으로 두 엔티티를 모두 조회한다.

        예제2) 즉시로딩 실행 SQL
            SELECT
                M.MEMBER_ID AS MEMBER_ID,
                M.TEAM_ID AS TEAM_ID,
                M.USERNAME AS USERNAME,
                T.TEAM_ID AS TEAM_ID,
                T.NAME AS NAME
            FROM
                MEMBER M LEFT OUTER JOIN TEAM T
                    ON M.TEAM_ID=T.TEAM_ID
            WHERE
                M.MEMBER_ID="member1"

        설명)
            예제2의 실행되는 SQL을 분석해보면 회원과 팀을 조인해서 쿼리 한 번으로 조회한 것을 알 수 있다.

        이후 member.getTea()을 호출하면 이미 로딩된 team1 엔티티를 반환 한다.
     */
    public static void eagerFetch(EntityManager em) {
        Member member = em.find(Member.class, "member1");
        Team team = member.getTeam(); // 객체 그래프 탐색
    }

    /*
        예제1) 즉시 로딩, 회원을 조회할 때 팀 지연 로딩
            1. 로딩 -> member1
            2. 지연 로딩 -> team1 (프록시 team1 엔티티)

        회원과 팀을 지연 로딩으로 설정했다. 따라서 em.find(Member.class, "member1")를 호출하면 회원만
        조회하고 팀은 조회하지 않는다. 대신에 예제1과 같이 조회한 회원의 team 멤버변수에 프록시 객체를 넣어둔다.

            Team team = member.getTeam(); // 프록시 객체

        반환된 팀 객체는 프록시 객체다. 이 프록시 객체는 실제 사용될 때까지 데이터로딩을 미룬다. 그래서 지연 로딩이라 한다.

            team.getName(); // 팀 객체 실제 사용

        이처럼 실제 데이터가 필요한 순간이 되어서야 데이터베이스를 조회해서 프록시 객체를 초기화한다.

        em.find(Member.class, "member1") 호출 시 실행되는 SQL은 다은과 같다.
            SELECT *
              FROM MEMBER
             WHERE MEMBER_ID = "member1"

        team.getName() 호출로 프록시 객체가 초기화되면서 실행되는 SQL은 다음과 같다.
            SELECT *
              FROM TEAM
             WHERE TEAM_ID = "team1"
     */
    public static void lazyFetch(EntityManager em) {
        Member member = em.find(Member.class, "member1");
        Team team = member.getTeam(); // 객체 그래프 탐색
        team.getName(); // 팀 객체 실제 사용
    }

}

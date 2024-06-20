package jpabook.start;

import org.hibernate.boot.registry.selector.StrategyRegistration;

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

}

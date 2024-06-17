package jpabook.start;

import javax.persistence.*;

/*
    프록시 (Proxy)
    ------------------------------------------------------------------------------------------------------------
    엔티티를 조회할 때 연관된 엔티티들이 항상 사용되는 것은 아니다. 예를 들어 회원 엔티티를
    조회할 때 연관된 팀 엔티티는 비즈니스 로직에 따라 사용될 때도 있지만 그렇지 않을 때도 있다.
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
        회원과 팀 정보를 출력하는 비즈니스 로직
        ------------------------------------------------------------------------------------------------------------
        이 메소드는 memberId로 회원 엔티티를 찾아서 회원은 물론이고 회원과 연관된 팀의 이름도 출력한다.
     */
    public static void printUserAndTeam(EntityManager em, String memberId) {
        Member member = em.find(Member.class, memberId);
        Team team = member.getTeam();
        System.out.println("회원 이름: " + member.getUsername());
        System.out.println("소속팀: " + team.getName());
    }

    /*
        회원 정보만 출력하는 비즈니스 로직
        ------------------------------------------------------------------------------------------------------------
        본 메소드는 회원 엔티티만 출력하는 데 사용하고 회원과 연관된 팀 엔티티는 전혀 사용하지 않는다.

        이 메소드는 회원 엔티티만 사용하므로 em.find()로 회원 엔티티를 조회할 때 회원과 연관된
        팀 엔티티(Member.team)까지 데이터베이스에서 함께 죄회해 두는 것은 효율적이지 않다.

        JPA는 이런 문제를 해결하려고 엔티티가 실제 사용될 때까지 데이터베이스 조회를 지연하는 방법을 제공하는데
        이것을 지연 로딩이라 한다. 쉽게 이야기해서 team.getName()처럼 팀 엔티티의 값을 실제 사용하는 시점에
        데이터베이스에서 팀 엔티티에 필요한 데이터를 조회하는 것이다. 이 방법을 사용하면 본 메소드는 회원 데이터만
        데이터베이스에서 조회해도 된다.
     */
    public static void printUser(EntityManager em, String memberId) {
        Member member = em.find(Member.class, memberId);
        System.out.println("회원 이름: " + member.getUsername());
    }

}

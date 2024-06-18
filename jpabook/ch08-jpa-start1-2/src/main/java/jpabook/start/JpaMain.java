package jpabook.start;

import javax.persistence.*;

/*
    프록시와 식별자
    ------------------------------------------------------------------------------------------------------------
    엔티티를 프록시로 조회할 때 식별자(PK) 값을 파라미터로 전달하는데 프록시 객체는 이 식별자 값을 보관한다.

    예제1)
        Team team = em.getReference(Team.class, "team1"); // 식별자 보관
        team.getId(); // 초기화되지 않음

    프록시 객체는 식별자 값을 가지고 있으므로 식별자 값을 조회하는 team.getId()를 호출해도 프록시를 초기화 하지 않는다.
    단 엔티티 접근 방식을 프로퍼티(@Access(AccessType.PROPERTY))로 설정한 경우에만 초기화 하지 않는다.

    엔티티 접근 방식을 필드(@Access(AccessType.FIELD))로 설정하면 JPA는 getId() 메소드가 id만 조회하는
    메소드인지 다른 필드까지 활용해서 어떤일을 하는 메소드인지 알지 못하므로 프록시 객체를 초기화한다.

    예제2) 프록시는 다음 코드처럼 연관관계를 설정할 때 유용하게 사용할 수 있다.
        Member member = em.find(Member.class, "member1");
        Team team = em.getReference(Team.class, "team1"); // SQL을 실행하지 않음
        member.setTeam(team);

    연관관계를 설정할 때는 식별자 값만 사용하므로 프록시를 사용하면 데이터베이스 접근 횟수를 줄일 수 있다.
    참고로 연관관계를 설정할 때는 엔티티 접근 방식을 필드로 설정해도 프록시를 초기화 하지 않는다.
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

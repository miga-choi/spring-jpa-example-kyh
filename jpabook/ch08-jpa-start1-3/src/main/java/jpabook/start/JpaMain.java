package jpabook.start;

import org.hibernate.boot.registry.selector.StrategyRegistration;

import javax.persistence.*;

/*
    프록시 확인
    ------------------------------------------------------------------------------------------------------------
    JPA가 제공하는 PersistenceUnitUtil.isLoaded(Object entity) 메소드를 사용하면 프록시 인스턴스의 초기화 여부를 확인할 수 있다.
    아식 초기화 되지 않은 프록시 인스턴스는 false를 반환한다. 이미 초기화 되었거나 프록시 인스턴스가 아니면 true를 반환한다.

    예제1)
        boolean isLoad = em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(entity);
        // 또는 boolean isLoad = emf.getPersistenceUnitUtil().isLoaded(entity);
        System.out.println("isLoad = " + isLoad); // 초기화 여부 확인

    조회한 엔티티가 진짜 엔티티인지 프록시로 조회한 것인지 확인하려면 클래스명을 직접 출력해보면 된다.
    다음 예를 보면 클래스 명 뒤에 ..javassist..라 되어 있는데 이것으로 프록시인 것을 확인할 수 있다.
    프록시를 생성하는 라이브러리에 따라 출력 결과는 달라질 수 있다.

    예제2)
        System.out.println("memberProxy = " + member.getClass().getName()); // 초기화 여부 확인
        // 결과: memberProxy = jpabook.domain.Member_$$_javassist_0
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
        System.out.println("회원 이름: " + member.getName());
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
        System.out.println("회원 이름: " + member.getName());
    }

    /*
        JPA에서 식별자로 엔티티 하나를 조회할 때는 EntityManager.find()를 사용한다.

        이 메소드는 영속성 컨텍스트에 엔티티가 없으면 데이터베이스를 조회한다.

        이렇게 엔티티를 직접 조회하면 조회한 엔티티를 실제 사용하든 사용하지 않든 데이터베이스를 조회하게 된다.
     */
    public static void getFromDB(EntityManager em, String memberId) {
        Member member = em.find(Member.class, "member1");
    }

    /*
        엔티티를 실제 사용하는 시점까지 데이터베이스 조회를 미루고 싶으면 EntityManager.getReference() 메소드를 사용하면 된다.

        이 메소드를 호출할 때 JPA는 데이터베이스를 조회하지 않고 실제 엔티티 객체도 생성하지 않는다.
        대신에 데이터베이스 접근을 위임한 프록시 객체를 반환한다.
     */
    public static void getFromProxy(EntityManager em, String memberId) {
        Member member = em.getReference(Member.class, "member1");
    }

    /*
        프록시 초기화 예제
     */
    public static void proxyInitializeExample(EntityManager em) {
        // MemberProxy 반환
        Member member = em.getReference(Member.class, "id1");
        member.getName(); // 1. getName();
    }

    /*
        준영속 상태와 초기화
        ------------------------------------------------------------------------------------------------------------
        준영속 상태와 초기화 관련된 코드
     */
    public static void proxyInitialize() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        // MemberProxy 반환
        Member member = em.getReference(Member.class, "id1");
        tx.commit();
        em.close(); // 영속성 컨텍스트 종료

        member.getName(); // 준영속 상태 초기화 시도, org.hibernate.LazyInitializationException 예외 발생
    }

    /*
        엔티티 프록시로 조회할 때 식별자(PK) 값을 파라미터로 전달하는데 프록시 객체는 이 식별자 값을 보관한다.
        ------------------------------------------------------------------------------------------------------------
        프록시 객체는 식별자 값을 가지고 있으므로 식별자 값을 조회하는 team.getId()를 호출해도 프록시를 초기화 하지 않는다.
        단 엔티티 접근 방식을 프로퍼티(@Access(AccessType.PROPERTY))로 설정한 경우에만 초기화 하지 않는다.

        엔티티 접근 방식을 필드(@Access(AccessType.FIELD))로 설정하면 JPA는 getId() 메소드가 id만 조회하는
        메소드인지 다른 필드까지 활용해서 어떤일을 하는 메소드인지 알지 못하므로 프록시 객체를 초기화한다.
     */
    public static void findByProxy(EntityManager em) {
        Team team = em.getReference(Team.class, "team1"); // 식별자 보관
        team.getId(); // 초기화되지 않음
    }

    /*
        프록시는 다음 코드처럼 연관관계를 설정할 때 유용하게 사용할 수 있다.
        ------------------------------------------------------------------------------------------------------------
        연관관계를 설정할 때는 식별자 값만 사용하므로 프록시를 사용하면 데이터베이스 접근 횟수를 줄일 수 있다.
        참고로 연관관계를 설정할 때는 엔티티 접근 방식을 필드로 설정해도 프록시를 초기화 하지 않는다.
     */
    public static void setRelationship(EntityManager em) {
        Member member = em.find(Member.class, "member1");
        Team team = em.getReference(Team.class, "team1"); // SQL을 실행하지 않음
        member.setTeam(team);
    }

    /*
        프록시 확인
        ------------------------------------------------------------------------------------------------------------
        JPA가 제공하는 PersistenceUnitUtil.isLoaded(Object entity) 메소드를 사용하면 프록시 인스턴스의 초기화 여부를 확인할 수 있다.
        아식 초기화 되지 않은 프록시 인스턴스는 false를 반환한다. 이미 초기화 되었거나 프록시 인스턴스가 아니면 true를 반환한다.

        조회한 엔티티가 진짜 엔티티인지 프록시로 조회한 것인지 확인하려면 클래스명을 직접 출력해보면 된다.
        다음 예를 보면 클래스 명 뒤에 ..javassist..라 되어 있는데 이것으로 프록시인 것을 확인할 수 있다.
        프록시를 생성하는 라이브러리에 따라 출력 결과는 달라질 수 있다.
     */
    public static void checkProxy(EntityManagerFactory emf, EntityManager em) {
        Member member = em.find(Member.class, "id1");

        boolean isLoad = em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(member);
        // 또는 boolean isLoad = emf.getPersistenceUnitUtil().isLoaded(member);

        System.out.println("isLoad = " + isLoad); // 초기화 여부 확인

        System.out.println("memberProxy = " + member.getClass().getName());
        // 결과: memberProxy = jpabook.domain.Member_$$_javassist_0
    }

}

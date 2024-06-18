package jpabook.start;

import javax.persistence.*;

/*
    프록시 기초
    ------------------------------------------------------------------------------------------------------------
    JPA에서 식별자로 엔티티 하나를 조회할 때는 EntityManager.find()를 사용한다.

    예제1 메소드는 영속성 컨텍스트에 엔티티가 없으면 데이터베이스를 조회한다.

    예제1)
        Member member = em.find(Member.class, "member1");

    이렇게 엔티티를 직접 조회하면 조회한 엔티티를 실제 사용하든 사용하지 않든 데이터베이스를 조회하게 된다.
    엔티티를 실제 사용하는 시점까지 데이터베이스 조회를 미루고 싶으면 EntityManager.getReference() 메소드를 사용하면 된다.

    예제2-1) 프록시 조회
        Client ---em.getReference()---> Proxy: Entity target = null, getId(), getName()
    예제2-2)
        Member member = em.getReference(Member.class, "member1");

    예제2-2 메소드를 호출할 때 JPA는 데이터베이스를 조회하지 않고 실제 엔티티 객체도 생성하지 않는다.
    대신에 데이터베이스 접근을 위임한 프록시 객체를 반환한다.
    ------------------------------------------------------------------------------------------------------------
    프록시의 특징:

    예제3) 프록시 구조
        Proxy ---상속---> Entity
        - Proxy: getId(), getName()
        - Entity: id, name, getId(), getName()

    예제3을 보자. 프록시 클래스는 실제 클래스를 상속 받아서 만들어지므로 실제 클래스와 겉 모양이 같다.
    따라서 사용하는 입장에서는 이것이 진짜 객체인지 프록시 객체인지 구분하지 않고 사용하면 된다.

    예제4) 프로시 위임
        Proxy ---위임(delegate)---> Entity
        - Proxy: Entity target, getId(), getName()
        - Entity: id, name, getId(), getName()

    예제4를 보자. 프록시 객체는 실제 객체에 대한 참조(target)를 보관한다.
    그리고 프록시 객체의 메소드를 호출하면 프록시 객체는 실제 객체의 메소드를 호출한다.
    ------------------------------------------------------------------------------------------------------------
    프록시 객체의 초기화:
    프록시 객체는 member.getName() 처럼 실제 사용될 때 데이터베이스를 조회해서 실제 엔티티 객체를 생성하는데 이것을 프록시 객체의 초기화라 한다.

    예제5) 프록시 초기화 예제
        // MemberProxy 반환
        Member member = em.getReference(Member.class, "id1");
        member.getName(); // 1. getName();

    예제6) 프록시 클래스 예상 코드
        class MemberProxy extends Member {
            Member target = null; // 실제 엔티티 참조

            public String getName() {
                if (target == null) {
                    // 2. 초기화 요청
                    // 3. DB 조회
                    // 4. 실제 엔티티 생성 및 참조 보관
                    this.target = ...;
                }

                // 5. target.getName();
                return target.getName();
            }
        }

    예제7) 프록시 초기화
        1. Client ---getName()---> MemberProxy: Member target, getId(), getName()
        2. MemberProxy ---초기화 요청---> 영속성 컨텍스트
        3. 영속성 컨텍스트 ---DB조회---> DB
        4. 영속성 컨텍스트 ---실제 Entity 생성---> Member: id, name, getId(), getName()
        5. MemberProxy ---target.getName()---> Member

    예제7와 예제6으로 프록시 초기화 과정을 분석해보자.
        1. 프록시 객체에 member.getName()을 호출해서 실제 데이터를 조회한다.
        2. 프록시 객체는 실제 엔티티가 생성되어 있지 않으면 영속성 컨텍스트에 실제 엔티티 생성을 요청하는데 이것을 초기화라 한다.
        3. 영속성 컨텍스트는 데이터베이스를 조회해서 실제 엔티티 객체를 생성한다.
        4. 프록시 객체는 생성된 실제 엔티티 객체의 참조를 Member target 멤버변수에 보관한다.
        5. 프록시 객체는 실제 엔티티 객체의 getName()을 호출해서 결과를 반환한다.
    ------------------------------------------------------------------------------------------------------------
    프록시의 특징:
        - 프록시 객체는 처음 사용할 때 한 번만 초기화 된다.
        - 프록시 객체를 초기화한다고 프록시 객체가 실제 엔티티로 바뀌는 것은 아니다.
          프록시 객체가 초기화되면 프록시 객체를 통해서 실제 엔티티에 접근할 수 있다.
        - 프록시 객체는 원본 엔티티를 상속받은 객체이므로 타입 체크 시에 주의해서 사용해야 한다.
        - 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 데이터베이스를 조회할 필요가 없으므로
          em.getReference()를 호출해도 프록시가 아닌 실제 엔티티를 반환한다.
        - 초기화는 영속성 컨텍스트의 도움을 받아야 가능하다. 따라서 영속성 컨텍스트의 도움을
          받을 수 없는 준영속 상태의 프록시를 초기화하면 문제가 발생한다. 하이버네이트는
          org.hibernate.LazyInitializationException 예외를 발생시킨다.
    ------------------------------------------------------------------------------------------------------------
    준영속 상태와 초기화:

    준영속 상태와 초기화에 관련된 코드는 다음과 같다:
        // MemberProxy 반환
        Member member = em.getReference(Member.class, "id1");
        transaction.commit();
        em.close(); // 영속성 컨텍스트 종료
        member.getName(); // 준영속 상태 초기화 시도, org.hibernate.LazyInitializationException 예외 발생

    위 코드를 보면 em.close() 메소드로 영속성 컨텍스트를 종료해서 member는 준영속 상태다. member.getName()을 호출하면
    프록시를 초기화해야 하는데 영속성 컨텍스트가 없으므로 실제 엔티티를 조회할 수 없다. 따라서 예외가 발생한다.
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

}

package jpabook.start;

import javax.persistence.*;

/*
    조인 테이블
    ------------------------------------------------------------------------------------------------------------
    데이터베이스 테이블의 연관관계를 설계하는 방법은 크게 2가지다:
    - 조인 컬럼 사용 (외래 키)
    - 조인 테이블 사용 (테이블 사용)

    ------------------------------------------------------------------------------------------------------------
    조인 컬럼 사용:
    테이블 간에 관계는 주로 조인 컬럼이라 부르는 외래 키 컬럼을 사용해서 관리한다.
    예제1)
        Member [1:1] LOCKER
        - MEMBER: MEMBER_ID (PK), USERNAME, LOCKER_ID (FK, NULL 허용)
        - LOCKER: LOCKER_ID (PK), NAME
    설명)
        위 예제에서 회원과 사물함이 있는데 각각 테이블에 데이터를 등록했다가 회원이 원할 때 사물함을 선택할 수 있다고 가정해보자.
        회원이 사물함을 사용하기 전까지는 아직 둘 사이에 관계가 없으므로 MEMBER 테이블의 LOCKER_ID 외래 키에 null을 입력해두어야 한다.
        이렇게 외래 키에 null을 허용하는 관계를 선택적 비식별 관계라 한다.
        선택적 비식별 관계는 외래 키에 null을 허용하므로 회원과 사물함을 조인할 때 외부 조인(OUTER JOIN)을 사용해야 한다.
        실수로 내부 조인을 사용하면 사물함과 관계가 없는 회원은 조회되지 않는다. 그리고 회원과 사물함이 아주 가끔 관계를 맺는다면 외래 키 값
        대부분이 null로 저장되는 단점이 있다.
    ------------------------------------------------------------------------------------------------------------
    조인 테이블 사용:
    예제2)
        MEMBER [1:N] MEMBER_LOCKER [N:1] LOCKER
        - MEMBER: MEMBER_ID (PK), USERNAME
        - MEMBER_LOCKER: MEMBER_ID (FK), LOCKER_ID (FK)
        - LOCKER: LOCKER_ID (PK), NAME
     설명)
        이 방법은 조인 테이블이라는 별도의 테이블을 사용해서 연관관계를 관리한다.
        위 두 예제를 비교해보면 조인 컬럼을 사용하는 방법과 조인 테이블을 사용하는 방법의 차이를 알 수 있다.
        조인 컬럼을 사용하는 방법은 단순히 외래 키 컬럼만 추가해서 연관관계 를 맺지만 조인 테이블을 사용하는
        방법은 연관관계를 관리하는 조인 테이블(MEMBER_LOCKER)을 추가하고 여기서 두 테이블의 외래 키를 가지고
        연관관계를 관리한다. 따라서 MEMBER와 LOCKER에는 연관관계를 관리하기 위한 외래 키 컬럼이 없다.
        예제2를 보면 회원과 사물함 데이터를 각각 등록했다가 회원이 원할 때 사물함을 선택하면 MEMBER_LOCKER 테이블에만
        값을 추가하면 된다.
        조인 테이블의 가장 큰 단점은 테이블을 하나 추가해야 한다는 점이다. 따라서 관리해야 하는 테이블이 늘어나고 회원과
        사물함 두 테이블을 조인하려면 MEMBER_LOCKER 테이블까지 추가로 조인해야 한다. 따라서 기본은 조인 컬럼을 사용하고
        필요하다고 판단되면 조인 테이블을 사용하자.
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

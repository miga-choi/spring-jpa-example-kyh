package jpabook.start;

import javax.persistence.*;

/*
    지금까지 학습한 상속 관계 매핑은 부모 클래스와 자식 클래스를 모두 데이터베이스 테이블과 매핑했다.
    부모 클래스는 테이블과 매핑하지 않고 부모 클래스를 상속받는 자식 클래스에게 매핑 정보만 제공하고
    싶으면 @MappedSuperclass를 사용하면 된다.
    ------------------------------------------------------------------------
    @MappedSuperclass는 비유를 하자면 추상 클래스와 비슷한데 @Entity는 실제 테이블과 매핑되지만
    @MappedSuperclass는 실제 테이블과는 매핑되지 않는다.
    이것은 단순히 매핑 정보를 상속할 목적으로만 사용된다.
    ------------------------------------------------------------------------
    ex)
    - BaseEntity: id, name
    - Member extends BaseEntity: [id, name], email
    - Seller extends BaseEntity: [id, name], shopName
    설명)
    회원(Member)과 판매자(Seller)는 서로 관계가 없는 테이블과 엔티티다.
    테이블은 그대로 두고 객체 모델의 id, name 두 공통 속성을 부모 클래스로 모으고 객체 상속 관계로 만들어 보자.
    ------------------------------------------------------------------------
    BaseEntity에는 객체들이 주로 사용하는 공통 매핑 정보를 정의했다.
    그리고 자식 엔티티들은 삭속을 통해 BaseEntity의 매핑 정보를 물려받았다.
    여기서 BaseEntity는 테이블과 매핑할 필요가 없고 자식 엔티티에게 공통으로 사용되는 매핑 정보만 제공하면 된다.
    따라서 @MappedSuperclass를 사용했다.
    ------------------------------------------------------------------------
    @MappedSuperclass 특징:
    - 테이블과 매핑되지 않고 자식 클래스에 엔티티의 매핑 정보를 상속하기 위해 사용한다.
    - @MappedSuperclass로 지정한 클래스는 엔티티가 아니므로 em.find()나 JPQL에서 사용할 수 없다.
    - 이 클래스를 직접 생성해서 사용할 일은 거의 없으므로 추상 클래스로 만드는 것을 권장한다.
    ------------------------------------------------------------------------
    정리하자면 @MappedSuperclass는 테이블과는 관계가 없고 단순히 엔티티가 공통으로 사용하는 매핑 정보를 모아주는 역할을 할 뿐이다.
    ORM에서 이야기하는 진정한 상속 매핑은 이전에 학습한 객체 상속을 데이터베이스의 슈퍼타입 서브타입 관계와 매핑하는 것이다.
    @MappedSuperclass를 사용하면 등록일자, 수정일자, 등록자, 수정자 같은 여러 엔티티에서 공통으로 사용하는 속성을 효과적으로 관리할 수 있다.
 */
@MappedSuperclass
/*
    부모로부터 물려받은 매핑 정보를 재정의하려면 @AttributeOverrides나 @AttributeOverride를 사용하고,
    연관관계를 재정의 하려면 @AttributeOverrides나 @AttributeOverride를 사용한다.
    ------------------------------------------------------------------------
    부모에게 상속받은 id 속성의 컬럼명을 MEMBER_ID로 재정의했다.
    둘 이상을 재정의하려면 @AttributeOverrides를 사용하면 된다.
 */
@AttributeOverride(
        name = "id",
        column = @Column(name = "MEMBER_ID")
)
@AttributeOverrides(
        {
                @AttributeOverride(
                        name = "id",
                        column = @Column(name = "MEMBER_ID")
                ),
                @AttributeOverride(
                        name = "name",
                        column = @Column(name = "MEMBER_NAME")
                )

        }
)
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

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
}

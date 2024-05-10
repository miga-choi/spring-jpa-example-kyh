package jpabook.start;

import javax.persistence.*;  //**
import java.util.Date;

/**
 * User: HolyEyE
 * Date: 13. 5. 24. Time: 오후 7:43
 */

/**
 * 객체와 테이블 매핑: @Entity, @Table
 * 기본키 매핑: @Id
 * 필드와 컬럼 매핑: @Column
 * 연관관계 매핑: @ManyToOne, @JoinColumn
 */
// @Entity
//   JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 어노테이션을 필수로 붙여야한다.
//   @Entity가 붙은 클래스는 JPA가 관리하는 것으로, 엔티티라 부른다.
// @Entity 적용 시 주의사항
//   1. 기본 생성자는 필수다 (파라미터가 없는 public 또는 protected 생성자).
//   2. final 클래스, enum, interface, inner 클래스에서는 사용할 수 없다.
//   3. 저장할 필드에 final을 사용하면 안 된다.
@Entity
@Table(
        // 테이블 이름
        name = "MEMBER",
        // 유니크 제약조건 추가
        uniqueConstraints = {
                @UniqueConstraint(
                        // 제약조건 명
                        name = "NAME_AGE_UNIQUE",
                        // 복합 유니크 제약조건
                        columnNames = {"NAME", "AGE"}
                )
        }
)
public class Member {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME", nullable = false, length = 10) //추가 //**
//    @Column(name = "NAME") //추가 //**
    private String username;

    private Integer age;

    //=== 추가
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;

    @Transient
    private String temp;

    /**
     * JPA가 엔티티 객체를 생성할 때 기본 생성자를 사용하므로 이 생성자는 반드시 있어야 한다.
     * 자바는 생성자가 하나도 없으면 다음과 같은 기본 생성자를 자동으로 만든다.
     */
    // 기본 생성자
    public Member() {
    }

    /**
     * 다음과 같이 생성자를 하나 이상 만들면 자바는 기본 생성자를 자동으로 만들지 않는다.
     * 이때는 기본 생성자를 직접 만들어야 한다.
     */
    // 임의의 생성자
    public Member(String username) {
        this.username = username;
    }

    //Getter, Setter

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}

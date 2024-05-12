package jpabook.start;

import javax.persistence.*;
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
// @Table
//   매핑할 DDL:
//     ALTER TABLE MEMBER
//     ADD CONSTRAINT [uniqueConstraints.name] UNIQUE (uniqueConstraints.columnNames)
//   생성된 DDL:
//     ALTER TABLE MEMBER
//     ADD CONSTRAINT [NAME_AGE_UNIQUE] UNIQUE (NAME, AGE)
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
// @SequenceGenerator
//   시퀀스 생성기
//   - name: 식별자 생성기 이름, 필수
//   - sequenceName: 데이터베이스에 등록되어 있는 시퀀스 이름, 기본값: hibernate_sequence
//   - initialValue: DDL 생성 시에만 사용됨, 시퀀스 DDL을 생성할 때 처음 시작하는 수를 지정한다, 기본값: 1
//   - allocationSize: 시퀀스 한 번 호출에 증가하는 수 (성능 최적화에 사용됨), 기본값: 50
//   - catalog: 데이터베이스 catalog 이름
//   - schema: 데이터베이스 schema 이름
//   매팽할 DDL:
//     CREATE SEQUENCE [sequenceName]
//     START WITH [initialValue] INCREMENT BY [allocationSize]
@SequenceGenerator(
        // 식별자 생성기 이름, 필수
        name = "MEMBER_SEQ_GENERATOR",
        // 데이터베이스에 등록되어 있는 시퀀스 이름, 기본값: hibernate_sequence
        sequenceName = "MEMBER_SEQ",
        // DDL 생성 시에만 사용됨, 시퀀스 DDL을 생성할 때 처음 시작하는 수를 지정한다, 기본값: 1
        initialValue = 1,
        // 시퀀스 한 번 호출에 증가하는 수 (성능 최적화에 사용됨), 기본값: 50
        allocationSize = 1
)
public class Member {
    /**
     * Id:
     * - 기본 키 (Primary Key)
     * GeneratedValue:
     * - 자동 생성 (대리 키 사용 방식)
     * - 생략 시 직접 할당
     * GenerationType:
     * - 자동 생성 기본 키 타입
     * 1. GenerationType.IDENTITY: 기본 키 생성을 데이터베이스에 위임한다.
     * 2. GenerationType.SEQUENCE: 데이터베이스 시퀀스를 사용해서 기본 키를 할당한다.
     * 3. GenerationType.TABLE: 키 생성 테이블을 사용한다.
     */
    @Id
    @Column(name = "ID")
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY 전략
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE, // SEQUENCE 전략
            generator = "MEMBER_SEQ_GENERATOR" // @SequenceGenerator로 등록한 시퀀스 생성키를 선택
    )
    private String id;

    /**
     * 제약조건
     * nullable: not null
     * length: 문자의 크기
     */
    @Column(name = "NAME", nullable = false, length = 10)
    private String username;

    private Integer age;

    /**
     * roleType: 자바의 enum을 사용해서 회원의 타입을 구분했다.
     * 일반 회원은 USER, 관리자는 ADMIN이다.
     * 이처럼 자바의 enum을 사용하려면 @Enumerated 어노테이션으로 매핑해야 한다.
     */
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    /**
     * createdDate: 자바의 날짜 타입은 @Temporal을 사용해서 매핑한다.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    /**
     * lastModifiedDate: 자바의 날짜 타입은 @Temporal을 사용해서 매핑한다.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    /**
     * description: 회원을 설명하는 필드는 길이 제한이 없다.
     * 따라서 데이터베이스의 VARCHAR 타입 대신에 CLOB 타입으로 저장해야 한다.
     *
     * @Lob을 사용하면 CLOB, BLOB 타입을 매핑할 수 있다.
     */
    @Lob
    private String description;

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
}

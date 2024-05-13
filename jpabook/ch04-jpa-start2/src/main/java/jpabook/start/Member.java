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
 * 필드와 컬럼 매핑:
 * - @Column: 컬럼을 매핑한다.
 * - @Enumerated: 자바의 enum 타입을 매핑한다.
 * - @Temporal: 날짜 타입을 매핑한다.
 * - @Lob: BLOB, CLOB 타입을 매핑한다.
 * - @Transient: 특정 필드를 데이터베이스에 매핑하지 않는다.
 * 기타:
 * - @Access: JPA가 엔티티에 접근하는 방식을 지정한다.
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
// TABLE 전략:
//   TABLE 전략은 키 생성 전용 테이블을 하나 만들고 여기에 이름과 값으로 사용할 컬럼을 만들어 데이터베이스 시퀀스를 흉내내는 전략이다.
//   이 전략은 테이블을 사용하므로 모든 데이터베이스에 적용할 수 있다.
// @TableGenerator
//   테이블 키 생성기
//   - name: 식별자 생성기 이름, 필수
//   - table: 키생성 테이블명, 기본값: hibernate_sequences
//   - pkColumnName: 시퀀스 컬럼명, 기본값: sequence_name
//   - valueColumnName: 시퀀스 값 컬럼명, 기본값: next_val
//   - pkColumnValue: 키로 사용할 값 이름, 기본값: 엔티티 이름
//   - initialValue: 초기 값, 마지막으로 생성된 값이 기준이다, 기본값: 0
//   - allocationSize: 시퀀스 한 번 호출에 증가하는 수 (성능 최적화에 사용됨), 기본값: 50
//   - catalog: 데이터베이스 catalog 이름
//   - schema: 데이터베이스 schema 이름
//   - uniqueConstraints: 유니크 제약 조건을 지정할 수 있다
//   TABLE 전략 키 생성 DDL:
//       CREATE TABLE [table] (
//         [pkColumnName] VARCHAR(255) NOT NULL,
//         [valueColumnName] BIGINT,
//         PRIMARY KEY ( [pkColumnName] )
//       )
@TableGenerator(
        name = "MEMBER_TABLE_SEQ_GENERATOR",
        table = "MY_TABLE_SEQUENCES",
        pkColumnName = "sequence_name",
        valueColumnName = "next_val",
        pkColumnValue = "MEMBER_TABLE_SEQ",
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
     * - 자동 생성 전략 (strategy)
     * -- 1. GenerationType.AUTO: 기본 값.
     * --    선택한 데이터베이스 방언에 따라 IDENTITY, SEQUENCE, TABLE 전략 중 하나를 자동으로 선택한다.
     * --    AUTO 전략의 장점은 데이터베이스를 변경해도 코드를 수정할 필요가 없다는 것이다.
     * --    특히 키 생성 전략이 아직 확정되지 않은 개발 초기 단계나 프로토타입 개발 시 편리하게 사용할 수 있다.
     * --    AUTO를 사용할 때 SEQUENCE나 TABLE 전략이 선택되면 시퀀스나 키 생성용 테이블을 미리 만들어 두어야 한다.
     * --    만약 스키마 자동 생성 기능을 사용한다면 하이버네이트가 기본값을 사용해서 적절한 시퀀스나 키 생성용 테이블을 만들어 줄 것이다.
     * -- 2. GenerationType.IDENTITY: 기본 키 생성을 데이터베이스에 위임한다.
     * -- 3. GenerationType.SEQUENCE: 데이터베이스 시퀀스를 사용해서 기본 키를 할당한다.
     * -- 4. GenerationType.TABLE: 키 생성 테이블을 사용한다.
     */
    @Id
    @Column(name = "ID")
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY 전략
    // @GeneratedValue(
    //         strategy = GenerationType.SEQUENCE, // SEQUENCE 전략
    //         generator = "MEMBER_SEQ_GENERATOR" // @SequenceGenerator로 등록한 시퀀스 생성키를 선택
    // )
    // @SequenceGenerator() // @GeneratedValue 옆에 사용해도 된다.
    // @GeneratedValue(
    //         strategy = GenerationType.TABLE,
    //         generator = "MEMBER_TABLE_SEQ_GENERATOR"
    // )
    @GeneratedValue(strategy = GenerationType.AUTO) // 기본 값
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

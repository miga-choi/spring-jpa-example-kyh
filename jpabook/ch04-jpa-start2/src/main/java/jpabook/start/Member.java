package jpabook.start;

import javax.persistence.*;
import java.util.Date;

/**
 * 연관관계 매핑 핵심 키워드:
 * 1. 방향(Direction): [단방향, 양방향]이 있다.
 * -  예를 들어 회원과 팀이 관계가 있을 때 회원 -> 팀 또는 팀 -> 회원 둘 중 한 쪽만 참조하는 것을 단방향 관계라 하고,
 * -  회원 -> 팀, 팀 -> 회원 양쪽 모두 서로 참조하는 것을 양방향 관계라 한다.
 * -  방향은 객체관계에만 존재하고 테이블 관계는 항상 양방향이다.
 * 2. 다중성(Multiplicity): [다대일(N:1), 일대다(1:N), 일대일(1:1), 다대다(N:M)] 다중성이 있다.
 * -  예를 들어 회원과 팀이 관계가 있을 때 여러 회원은 한 팀에 속하므로 회원과 팀은 다대일 관계다.
 * -  반대로 한 팀에 여러 회원이 소속될 수 있으므로 팀과 회원은 일대다 관계다.
 * 3. 연관관계의 주인(owner):
 * -  객체를 양방향 연관관계로 만들면 연관관계의 주인을 정해야 한다.
 * ------------------------------------------------------------------------------------------------------------
 * 단방향 연관관계:
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
// @SequenceGenerator(
//         // 식별자 생성기 이름, 필수
//         name = "MEMBER_SEQ_GENERATOR",
//         // 데이터베이스에 등록되어 있는 시퀀스 이름, 기본값: hibernate_sequence
//         sequenceName = "MEMBER_SEQ",
//         // DDL 생성 시에만 사용됨, 시퀀스 DDL을 생성할 때 처음 시작하는 수를 지정한다, 기본값: 1
//         initialValue = 1,
//         // 시퀀스 한 번 호출에 증가하는 수 (성능 최적화에 사용됨), 기본값: 50
//         allocationSize = 1
// )
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
// @TableGenerator(
//         name = "MEMBER_TABLE_SEQ_GENERATOR",
//         table = "MY_TABLE_SEQUENCES",
//         pkColumnName = "sequence_name",
//         valueColumnName = "next_val",
//         pkColumnValue = "MEMBER_TABLE_SEQ",
//         allocationSize = 1
// )
// Access:
//   JPA가 엔티티 데이터에 접근하는 방식을 지정한다.
// - 필드 접근: AccessType.FIELD로 지정한다. 필드에 직접 접근한다. 필드 접근 권한이 private이어도 접근할 수 있다.
// - 프로퍼티 접근: AccessType.PROPERTY로 지정한다. 접근자 Getter를 사용한다.
// - Access를 설정하지 않으면 @Id의 위치를 기준으로 접근 방식이 설정된다.
// - 본 예제는 @Id가 필드에 있으므로 @Access(AccessType.FIELD)로 설정한 것과 같다. 따라서 @Access는 생략해도 된다.
@Access(AccessType.FIELD)
public class Member {
    /**
     * Id:
     * - 기본 키 (Primary Key)
     * ------------------------------------------------------------------------------------------------
     * Column:
     * - @Column은 객체 필드를 테이블 컬럼에 매핑한다.
     * - name:
     * --  필드와 매핑할 테이블의 컬럼 이름.
     * --  기본값: 객체의 필드 이름.
     * - insertable:
     * --  (거의 사용하지 않음) 엔티티 저장 시 이 필드도 같이 저장한다.
     * --  false로 설정하면 이 필드는 데이터베이스에 저장하지 않는다.
     * --  false 옵션은 읽기 전용일 때 사용한다.
     * --  기본값: true.
     * - updatable:
     * --  (거의 사용하지 않음) 엔티티 수정 시 이 필드도 같이 수정한다.
     * --  false로 설정하면 이 필드는 데이터베이스에 수정하지 않는다.
     * --  false 옵션은 읽기 전용일 때 사용한다.
     * --  기본값: true.
     * - table:
     * --  (거의 사용하지 않음) 하나의 엔티티를 두 개 이상의 테이블에 매핑할 때 사용한다.
     * --  지정한 필드를 다른 테이블에 매핑할 수 있다.
     * --  기본값: 현재 클래스가 매핑된 테이블.
     * - nullable:
     * --  (DDL) null 값의 허용 여부를 설정한다.
     * --  false로 설정하면 DDL 생성 시에 not null 제약조건이 붙는다.
     * --  기본값: true.
     * - unique:
     * --  (DDL) @Table의 uniqueConstraints와 같지만 한 컬럼에 간단히 유니크 제약조건을 걸 때 사용한다.
     * --  만약 두 컬럼 이상을 사용해서 유니크 제약조건을 사용하려면 클래스 레벨에서 @Table.uniqueConstraints를 사용해야 한다.
     * - columnDefinition:
     * --  (DDL) 데이터베이스 컬럼 정보를 직접 줄 수 있다.
     * --  기본값: 필드의 자바 타입과 방언 정보를 사용해서 적절한 컬럼 타입을 생성한다.
     * - length:
     * --  (DDL) 데이터베이스 컬럼 정보를 직접 줄 수 있다.
     * --  기본값: 255
     * - percision, scale:
     * --  (DDL) BigDecimal 타입에서 사용한다 (BigInteger도 사용할 수 있다).
     * --  percision은 소수점을 포함한 전체 자릿수를, scale은 소수의 자릿수다.
     * --  참고로 double, float 타입에는 적용되지 않는다.
     * --  아주 큰 숫자나 정밀한 소수를 다루어야 할 때만 사용한다.
     * --  기본값: percision=19, scale=2.
     * ------------------------------------------------------------------------------------------------
     * GeneratedValue:
     * - 자동 생성 (대리 키 사용 방식)
     * - 생략 시 직접 할당
     * GenerationType:
     * - 자동 생성 전략 (strategy)
     * --  1. GenerationType.AUTO: 기본 값.
     * --     선택한 데이터베이스 방언에 따라 IDENTITY, SEQUENCE, TABLE 전략 중 하나를 자동으로 선택한다.
     * --     AUTO 전략의 장점은 데이터베이스를 변경해도 코드를 수정할 필요가 없다는 것이다.
     * --     특히 키 생성 전략이 아직 확정되지 않은 개발 초기 단계나 프로토타입 개발 시 편리하게 사용할 수 있다.
     * --     AUTO를 사용할 때 SEQUENCE나 TABLE 전략이 선택되면 시퀀스나 키 생성용 테이블을 미리 만들어 두어야 한다.
     * --     만약 스키마 자동 생성 기능을 사용한다면 하이버네이트가 기본값을 사용해서 적절한 시퀀스나 키 생성용 테이블을 만들어 줄 것이다.
     * --  2. GenerationType.IDENTITY: 기본 키 생성을 데이터베이스에 위임한다.
     * --  3. GenerationType.SEQUENCE: 데이터베이스 시퀀스를 사용해서 기본 키를 할당한다.
     * --  4. GenerationType.TABLE: 키 생성 테이블을 사용한다.
     */
    @Id // @Id가 FIELD에 지정됨
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
     * ------------------------------------------------------------------------------------------------
     * EnumType:
     * -  ORDINAL:
     * --   EnumType.ORDINAL은 enum에 정의된 순서대로 ADMIN은 0, USER는 1값이 데이터베이스에 저장된다.
     * --   장점: 데이터베이스에 저장되는 데이터 크기가 작다.
     * --   단점: 이미 저장된 enum의 순서를 변경할 수 없다.
     * -  STRING:
     * --   EnumType.STRING은 enum 이름 그대로 ADMIN은 "ADMIN", USER는 "USER"라는 문자로 데이터베이스에 저장된다.
     * --   장점: 저장된 enum의 순서가 바뀌거나 enum이 추가되어도 안전하다.
     * --   단점: 데이터베이스에 저장되는 데이터 크기가 ORDINAL에 비해서 크다.
     */
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    /**
     * createdDate: 자바의 날짜 타입은 @Temporal을 사용해서 매핑한다.
     * ------------------------------------------------------------------------------------------------
     * Temporal:
     * -  날짜 타입(java.util.Date, java.util.Calendar)을 매핑할 때 사용한다.
     * -- value:
     * --   TemporalType은 필수로 지정해야 한다.
     * TemporalType:
     * -  TemporalType.DATE:
     * --   날짜, 데이터베이스 date 타입과 매핑 (예: 2013-10-11)
     * -  TemporalType.DATE:
     * --   시간, 데이터베이스 time 타입과 매핑 (예: 11:11:11)
     * -  TemporalType.TIMESTAMP:
     * --   날짜와 시간, 데이터베이스 timestamp 타입과 매핑 (예: 2013-10-11 11:11:11)
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
     * ------------------------------------------------------------------------------------------------
     * Lob:
     * -  데이터베이스 BLOB, CLOB 타입과 매핑한다.
     * -  @Lob에는 지정할 수 있는 속성이 없다. 대신에 매핑하는 필드 타입이 문자면 CLOB으로 매핑하고 나머지는 BLOB으로 매핑한다.
     * -- CLOB: String, char[], java.sql.CLOB
     * -- BLOB: byte[], java.sql.BLOB
     */
    @Lob
    private String description;

    /**
     * Transient:
     * -  이 필드는 매핑하지 않는다. 따라서 데이터베이스에 저장하지 않고 조회하지도 않는다. 객체에 임시로 어떤 값을 보관하고 싶을 때 사용한다.
     */
    @Transient
    private Integer temp;

    /**
     * 객체 연관관계: 회원 객체의 Member.team 필드 사용
     * 테이블 연관관계: 회원 테이블의 MEMBET.TEAM_ID 외래 키 컬럼을 사용
     * ------------------------------------------------------------------------------------------------
     * ManyToOne
     * - 이름 그대로 다대이(N:1) 관계라는 매핑 정보다.
     * - 회원과 팀은 다대일 관계다.
     * - 연관관계를 매핑할 때 이렇게 다중성을 나타내는 어노테이션을 필수로 사용해야 한다.
     * optional:
     * - false로 설정하면 연관된 엔티티가 항상 있어야 한다. 기본값: true.
     * fetch:
     * - 글로벌 페치 전략을 설정한다. 기본값: @ManyToOne=FetchType.EAGER, @OneToMany=FetchType.LAZY
     * cascade:
     * - 영속성 전이 기능을 사용한다.
     * targetEntity:
     * - 연관된 엔티티의 타입 정보를 설정한다. 이 기능은 거의 사용하지 않는다. 컬럭션을 사용해도 제네릭으로 타입 정보를 알 수 있다.
     * ------------------------------------------------------------------------------------------------
     * JoinColumn(name = "TEAM_ID")
     * - 조인 컬럼은 외래 키를 매핑할 때 사용한다.
     * - name 속성에는 매핑할 외래 키 이름을 지정한다.
     * - 회원과 팀 테이블은 TEAM_ID 외래 키로 연관관계를 맺으므로 이 값을 지정하면 된다.
     * - 이 어노테이션은 생략할 수 있다.
     * P.S.: @JoinColumn 생략:
     * - @JoinColumn을 생략하면 외래 키를 찾을 때 기본 전략을 사용한다.
     * - 기본전략: 필드명 + _ + 참조하는 외래 키를 찾을 때 기본 전략을 사용한다.
     * name:
     * - 매핑할 외래 키 이름. 기본값: 필드명 + _ + 참조하는 테이블의 기본 키 컬럼명.
     * referencedColumnName:
     * - 외래 키가 참조하는 대상 테이블의 컬럼명. 기본값: 참조하는 테이블의 기본 키 컬럼명.
     * foreignKey:
     * - (DDL) 외래 키 제약조건을 직접 지정할 수 있다. 이 속성은 테이블을 생성할 때만 사용한다.
     * unique, nullable, insertable, updatable, columnDefinition, table:
     * - @Column의 속성과 같다.
     */
    // 연관관계 매핑
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    // Getter, Setter

    // 연관관계 설정
    public void setTemm(Team temm) {
        this.team = temm;
    }

    public Team getTeam() {
        return team;
    }

    // @Id // @Id가 PROPERTY에 지정됨
    // @Id가 프로퍼티에 있으므로 @Access(AccessType.PROPERTY)로 설정한 것과 같다. 따라서 @Access는 생략해도 된다.
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

    // 필드, 프로퍼티 접근 함께 사용
    // @Id가 필드에 있으므로 기본은 필드 접근 방식을 사용하고 getFullName()만 프로퍼티 접근 방식을 사용한다.
    // 따라서 회원 엔티티를 저장하면 회원 테이블의 FULLNAME 컬럼에 firstName + lastName의 결과가 저장된다.
    // @Access(AccessType.PROPERTY)
    // public String getFullName() {
    //     return firstName + lastName;
    // }
}

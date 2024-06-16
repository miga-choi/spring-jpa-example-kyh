package jpabook.start;

import javax.persistence.*;

/*
    Board 엔티티는 @Table을 사용해서 BOARD 테이블과 매핑했다.
    그리고 @SecondaryTable을 사용해서 BOARD_DETAIL 테이블을 추가로 매핑했다.
    ------------------------------------------------------------------------------------------------------------
    @SecondaryTable 속성은 다음과 같다.
    - @SecondaryTable.name: 매핑핼 다른 테이블의 이름, 예제에서는 테이블명을 BOARD_DETAIL로 지정했다.
    - @SecondaryTable.pkJoinColumns: 매핑할 다른 테이블의 기본 키 컬럼 속성, 예제에서는 기본 키 컬럼명을 BOARD_DETAIL_ID로 지정했다.
        ex)
            @Column(table = "BOARD_DETAIL")
            private String content

    content 필드는 @Column(table = "BOARD_DETAIL")을 사용해서 BOARD_DETAIL 테이블의 컬럼에 매핑했다.
    샤싣 필드처럼 테이블을 지정하지 않으면 기본 테이블인 BOARD에 매핑된다.

    더 많은 테이블을 매핑하려면 @SecondaryTables를 사용하면 된다.
        ex)
            @SecondaryTables({
                @SecondaryTable(name = "BOARD_DETAIL"),
                @SecondaryTable(name = "BOARD_FILE"),
            })

    참고로 @SecondaryTable을 사용해서 두 테이블을 하나의 엔티티에 매핑하는 방법보다는 테이블당 엔티티를
    각각 만들어서 일대일 매핑하는 것을 권장한다. 이 방법은 항상 두 테이블을 조회하므로 최적화하기 어렵다.
    반변에 일대일 매핑은 원하는 부분만 조회할 수 있고 필요하면 둘을 함께 조회하면 된다.
 */
@Entity
@Table(name = "BOARD")
@SecondaryTable(
        name = "BOARD_DETAIL",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "BOARD_DETAIL<ID")
)
public class Board {
    @Id
    @GeneratedValue
    @Column(name = "BOARD_ID")
    private Long id;

    private String title;

    @Column(table = "BOARD_DETAIL")
    private String content;

    // Getter, Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

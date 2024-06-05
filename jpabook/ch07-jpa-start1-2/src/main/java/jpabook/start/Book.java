package jpabook.start;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/*
    기본값으로 자식 테이블은 부모 테이블의 ID 컬럼명을 그대로 사용하는데,
    만약 자식 테이블의 기본 키 컬럼명을 변경하고 싶으면 @PrimaryKeyJoinColumn을 사용하면 된다.
 */
@Entity
@DiscriminatorValue("B")
@PrimaryKeyJoinColumn(name = "BOOK_ID")
public class Book extends Item {
    private String author; // 작가
    private String isbn;   // ISBN
}

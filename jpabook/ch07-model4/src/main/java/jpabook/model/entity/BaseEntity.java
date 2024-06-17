// BaseEntity.java
package jpabook.model.entity;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by 1001218 on 15. 4. 5..
 */
/*
    @MappedSuperclass 매핑
    두번째 요구사항을 만족하려면 모든 테이블에 등록일과 수정일 컬럼을 우선 추가해야 한다.
    그리고 모든 엔티티에 등록일과 수정일을 추가하면 된다.
    이때 모든 엔티티에 등록일과 수정일을 직접 추가하는 것보다는 @MappedSuperclass를
    사용해서 부모 클래스를 만들어 상속받는 것이 효과적이다.
 */
@MappedSuperclass
public class BaseEntity {
    private Date createdDate;       // 등록일
    private Date lastModifiedDate;  // 수정일

    // Getter, Setter
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
}

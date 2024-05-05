package jpabook.start;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * User: HolyEyE
 * Date: 13. 5. 24. Time: 오후 7:43
 */
@Entity
// @org.hibernate.annotations.DynamicInsert
//      데이터를 저장할 때 데이터가 존재하는(null이 아닌) 필드만으로 INSERT SQL을 동적으로 생성
@DynamicInsert
// @org.hibernate.annotations.DynamicUpdate
//      수정 된 데이터만 사용해서 동적으로 UPDATE SQL을 생성
@DynamicUpdate
@Table(name = "MEMBER")
public class Member {
    @Id
    @Column(name = "ID")
    private String id; // 아이디

    @Column(name = "NAME")
    private String username; // 이름

    // 매핑 정보가 없는 필드
    private Integer age; // 나이

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
}

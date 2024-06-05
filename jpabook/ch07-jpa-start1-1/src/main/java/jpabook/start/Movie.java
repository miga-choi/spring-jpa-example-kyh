package jpabook.start;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/*
    @DiscriminatorValue("M"):
    엔티티를 저장할 떄 구분 컬럼에 입력할 값을 지정한다. 만약 영화 엔티티를 저장하면 구분 컬럼인 DTYPE에 값 M이 저장된다.
 */
@Entity
@DiscriminatorValue("M")
public class Movie extends Item {
    private String director; // 감독
    private String actor;    // 배우

    // Getter, Setter
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
}

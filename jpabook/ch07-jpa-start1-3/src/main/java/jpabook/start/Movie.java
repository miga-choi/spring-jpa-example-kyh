package jpabook.start;

import javax.persistence.Entity;

@Entity
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

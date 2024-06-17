// Album.java
package jpabook.model.entity.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by holyeye on 2014. 3. 11..
 */

@Entity
/*
    자식 테이블들은 @DiscriminatorValue 어노테이션을 사용하고 그 값으로 구분
    컬럼(DTYPE)에 입력될 값을 정하면 된다. 각각 앞자리를 따서 A, B, M으로 정했다.
 */
@DiscriminatorValue("A")
public class Album extends Item {
    private String artist;
    private String etc;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    @Override
    public String toString() {
        return "Album { " +
                "artist=\"" + artist + "\"" +
                ", etc=\"" + etc + "\"" +
                " }";
    }
}

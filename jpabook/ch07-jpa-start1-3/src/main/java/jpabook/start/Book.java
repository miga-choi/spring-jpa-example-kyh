package jpabook.start;

import javax.persistence.Entity;

@Entity
public class Book extends Item {
    private String author; // 작가
    private String isbn;   // ISBN
}

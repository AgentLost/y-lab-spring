package com.edu.ulab.app.entity;



import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "book", schema = "ulab_edu")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "sequence", allocationSize = 100)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private long pageCount;

    @Column(name = "person_id", nullable = false)
    private Long userId;

    public Book(long id, long user_id, String title, String author, long page_count) {
        this.id = id;
        this.userId = user_id;
        this.title = title;
        this.author = author;
        this.pageCount = page_count;
    }
}
package com.edu.ulab.app.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;


@Entity
@Data
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String title;
    private int age;

    @ToString.Exclude
    @OneToMany()
    @JoinColumn(name = "userId")
    Set<Book> books;
}

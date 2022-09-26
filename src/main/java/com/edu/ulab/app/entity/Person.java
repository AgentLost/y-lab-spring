package com.edu.ulab.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;


@Entity
@Data
@ToString
@NoArgsConstructor
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
    private Set<Book> books;

    public Person(Long id, String fullName, String title, int age) {
        this.id = id;
        this.fullName = fullName;
        this.title = title;
        this.age = age;
    }
}

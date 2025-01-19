package com.deploy.aws.entities;

import jakarta.persistence.*;
import lombok.*;

/*
    lombok dependency is handling Getters, Setters and Constructors by itself.
*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "students")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;
}

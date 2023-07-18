package com.springboot.blog.entity;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rol_id_generator")
    @SequenceGenerator(name = "rol_id_generator",
            sequenceName = "rol_id_sequence",
            allocationSize = 1)
    private Long id;
    @Column(length = 60)
    private String name;
}

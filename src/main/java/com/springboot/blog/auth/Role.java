package com.springboot.blog.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

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

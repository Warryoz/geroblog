package com.springboot.blog.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_generator")
    @SequenceGenerator(name = "user_id_generator",
            sequenceName = "user_id_sequence",
            allocationSize = 1)
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;
}

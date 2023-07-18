package com.springboot.blog.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/* We remove @Data cause at the moment to mapper the  comments, the string internal method failed,
so we need just the setter and getter */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table( name = "posts",  uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
public class Post {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post_id_generator")
    @SequenceGenerator(name = "post_id_generator",
            sequenceName = "post_id_sequence",
            allocationSize = 1)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

}

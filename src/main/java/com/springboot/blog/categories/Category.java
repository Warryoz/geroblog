package com.springboot.blog.categories;

import com.springboot.blog.posts.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_sequence")
    @SequenceGenerator(name = "category_id_sequence", sequenceName = "category_id_sequence", allocationSize = 1)
    private Long id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts;

}

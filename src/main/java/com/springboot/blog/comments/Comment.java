package com.springboot.blog.comments;

import com.springboot.blog.posts.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_id_generator")
    @SequenceGenerator(name = "comment_id_generator",
            sequenceName = "comment_id_sequence",
            allocationSize = 1)
    private Long id;

    private String name;
    private String email;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}

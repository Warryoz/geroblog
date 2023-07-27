package com.springboot.blog.categories;

import com.springboot.blog.categories.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

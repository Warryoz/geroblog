package com.springboot.blog.categories;

import com.springboot.blog.categories.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryById(Long id);
    CategoryDto createCategory(CategoryDto category);
    CategoryDto updateCategory(Long id, CategoryDto category);
    String deleteCategory(Long id);


}

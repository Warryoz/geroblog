package com.springboot.blog.categories;

import com.springboot.blog.categories.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.categories.CategoryDto;
import com.springboot.blog.categories.CategoryRepository;
import com.springboot.blog.categories.CategoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final ModelMapper mapper;
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", id)
        );

        return mapToDto(category);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = mapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return mapToDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", id)
        );
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        Category updatedCategory = categoryRepository.save(category);

        return mapToDto(updatedCategory);
    }

    @Override
    public String deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", id)
        );

        categoryRepository.deleteById(id);

        return "Category deleted successfully";
    }

    private CategoryDto mapToDto(Category category) {
        return mapper.map(category, CategoryDto.class);
    }

    private Category mapToEntity(CategoryDto categoryDto) {
        return mapper.map(categoryDto, Category.class);
    }

}

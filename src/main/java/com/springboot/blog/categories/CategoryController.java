package com.springboot.blog.categories;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CRUD REST APIs for category resource")
@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(
            summary = "Get all categories REST API",
            description = "Get all categories"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Categories successfully retrieved"
    )
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @Operation(
            summary = "Get category by id REST API",
            description = "Get category by id categories"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Category successfully retrieved"
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @Operation(
            summary = "Create new category REST API",
            description = "Create new category source"
    )
    @ApiResponse(
            responseCode =  "201",
            description = "Category successfully created"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDto));
    }

    @Operation(
            summary = "Update category by id REST API",
            description = "Update category by id source"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Category successfully updated"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable(name = "id") Long id, @Valid @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDto));
    }

    @Operation(
            summary = "Delete category by id REST API",
            description = "Delete category by id source"
            )
    @ApiResponse(
            responseCode = "200",
            description = "Category successfully deleted"
            )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
}

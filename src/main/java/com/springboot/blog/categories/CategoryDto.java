package com.springboot.blog.categories;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Category model information")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;

    @Schema(description = "Category name")
    @NotEmpty
    @Size(min = 4, message = "Category name should have at least 4 characters")
    private String name;

    @Schema(description = "Description of category")
    @NotEmpty
    private String description;
}

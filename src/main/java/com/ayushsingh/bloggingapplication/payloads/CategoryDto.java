package com.ayushsingh.bloggingapplication.payloads;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    
    private Integer categoryId;

    @NotBlank(message="Category name cannot be empty")
    @Size(min=4,max=50)
    private String categoryName;
    
    @NotBlank(message="Category description cannot be empty")
    @Size(min=10,message="Category description should be atleast 10 characters long")
    private String categoryDescription;
}

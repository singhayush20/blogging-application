package com.ayushsingh.bloggingapplication.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayushsingh.bloggingapplication.payloads.ApiResponse;
import com.ayushsingh.bloggingapplication.payloads.CategoryDto;
import com.ayushsingh.bloggingapplication.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto newCategoryDto = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(newCategoryDto, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Integer catId,
        @Valid    @RequestBody CategoryDto categoryDto) {
        CategoryDto newCategoryDto = this.categoryService.updateCategory(categoryDto, catId);
        return new ResponseEntity<CategoryDto>(newCategoryDto, HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{catId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {
        this.categoryService.deleteCategorybyId(catId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("category is deleted successfully", true),
                HttpStatus.OK);
    }

    // get by id
    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer catId) {
        CategoryDto categoryDto = this.categoryService.getCategoryById(catId);
        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
    }

    // get
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategories() {
        List<CategoryDto> categoryDto = this.categoryService.getAllCategories();
        return new ResponseEntity<List<CategoryDto>>(categoryDto, HttpStatus.OK);
    }
}

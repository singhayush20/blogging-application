package com.ayushsingh.bloggingapplication.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayushsingh.bloggingapplication.constants.AppConstants;
import com.ayushsingh.bloggingapplication.payloads.ApiResponse;
import com.ayushsingh.bloggingapplication.payloads.CategoryDto;
import com.ayushsingh.bloggingapplication.payloads.SuccessResponse;
import com.ayushsingh.bloggingapplication.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/blog/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // create
    @PostMapping("/create")
    public ResponseEntity<SuccessResponse<CategoryDto>> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto newCategoryDto = this.categoryService.createCategory(categoryDto);
            SuccessResponse<CategoryDto> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE, AppConstants.SUCCESS_MESSAGE, newCategoryDto);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    // update
    @PutMapping("/update")
    public ResponseEntity<SuccessResponse<CategoryDto>> updateCategory(@RequestParam(name="categoryid") Integer catId,
        @Valid    @RequestBody CategoryDto categoryDto) {

        CategoryDto updatedCategoryDto = this.categoryService.updateCategory(categoryDto, catId);
        SuccessResponse<CategoryDto> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE, AppConstants.SUCCESS_MESSAGE, updatedCategoryDto);

        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@RequestParam(name="categoryid") Integer catId) {
        this.categoryService.deleteCategorybyId(catId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully", AppConstants.SUCCESS_CODE, AppConstants.SUCCESS_MESSAGE),
                HttpStatus.OK);
    }

    // get by id
    @GetMapping("/get-by-id")
    public ResponseEntity<CategoryDto> getCategoryById(@RequestParam(name="categoryid") Integer catId) {
        CategoryDto categoryDto = this.categoryService.getCategoryById(catId);
        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
    }

    // get
    @GetMapping("/get-all")
    public ResponseEntity<SuccessResponse<List<CategoryDto>>> getCategories() {
        List<CategoryDto> categoryDto = this.categoryService.getAllCategories();
        SuccessResponse<List<CategoryDto>> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,categoryDto);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }
}

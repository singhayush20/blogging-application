package com.ayushsingh.bloggingapplication.services;

import java.util.List;

import com.ayushsingh.bloggingapplication.payloads.CategoryDto;

public interface CategoryService {
    //create
    public CategoryDto createCategory(CategoryDto categoryDto);
    //update
    public CategoryDto updateCategory(CategoryDto categoryDto,Integer  oldCategoryId);
    //delete
    public void deleteCategorybyId(Integer categoryId);
    //get
    public CategoryDto getCategoryById(Integer  categoryId);
    //get All
    public List<CategoryDto> getAllCategories();

}

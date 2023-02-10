package com.ayushsingh.bloggingapplication.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayushsingh.bloggingapplication.entities.Category;
import com.ayushsingh.bloggingapplication.exceptions.ResourceNotFoundException;
import com.ayushsingh.bloggingapplication.exceptions.DuplicateResourceException;

import com.ayushsingh.bloggingapplication.payloads.CategoryDto;
import com.ayushsingh.bloggingapplication.repositories.CategoryRep;
import com.ayushsingh.bloggingapplication.services.CategoryService;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRep categoryRep;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Optional<Category> category = this.categoryRep.findByCategoryName(categoryDto.getCategoryName());
        if (category.isPresent()) {
            throw new DuplicateResourceException("Category", "email", categoryDto.getCategoryName());
        }
        Category addedCategory = categoryRep.save(this.modelMapper.map(categoryDto, Category.class));

        return this.modelMapper.map(addedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer oldCategoryId) {
        Category oldCategory = categoryRep.findById(oldCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", oldCategoryId));
        oldCategory.setCategoryName(categoryDto.getCategoryName());
        oldCategory.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = this.categoryRep.save(oldCategory);
        return this.modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategorybyId(Integer categoryId) {
        Category oldCategory = categoryRep.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        // oldCategory.removeUsers();
        this.categoryRep.delete(oldCategory);

    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category oldCategory = categoryRep.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

        return this.modelMapper.map(oldCategory, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = this.categoryRep.findAll();
        List<CategoryDto> categoriesDto = categories.stream()
                .map((category) -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        return categoriesDto;
    }

}

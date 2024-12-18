package com.notes.service;

import com.notes.dto.CategoryDto;
import com.notes.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    public Boolean saveCategory(CategoryDto categoryDto);

    public List<CategoryDto> getAllCategory();

    public List<CategoryResponse> getActiveCategory();

    public CategoryDto getCategoryById(Integer id) throws Exception;

    public Boolean deleteCategory(Integer id);
}

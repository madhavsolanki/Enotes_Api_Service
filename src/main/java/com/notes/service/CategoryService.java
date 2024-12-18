package com.notes.service;

import com.notes.dto.CategoryDto;
import com.notes.dto.CategoryResponse;
import com.notes.entity.Category;

import java.util.List;

public interface CategoryService {

    public Boolean saveCategory(CategoryDto categoryDto);

    public List<CategoryDto> getAllCategory();

    public List<CategoryResponse> getActiveCategory();
}

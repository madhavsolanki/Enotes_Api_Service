package com.notes.impl;

import com.notes.dto.CategoryDto;
import com.notes.dto.CategoryResponse;
import com.notes.entity.Category;
import com.notes.repository.CategoryRepository;
import com.notes.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {

        // Using Model Mapper
        Category category =  mapper.map(categoryDto, Category.class);
        
        category.setIsDeleted(false);
        category.setCreatedBy(1);
        category.setCreatedOn(new Date());
        Category savedCategory = categoryRepo.save(category);
        if (ObjectUtils.isEmpty(savedCategory)){
            return false;
        }
        return true;

        //======================= Without Using Mapper ---------------------------
        // Category category = new Category();
        // category.setName(categoryDto.getName());
        // category.setDescription(categoryDto.getDescription());
        // category.setIsActive(categoryDto.getIsActive());

        // category.setIsDeleted(false);
        // category.setCreatedBy(1);
        // category.setCreatedOn(new Date());
        // Category savedCategory = categoryRepo.save(category);
        // if (ObjectUtils.isEmpty(savedCategory)){
        //     return false;
        // }
        // return true;
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepo.findAll();

        List<CategoryDto> categoryDtoList =  categories.stream().map(cat->mapper.map(cat, CategoryDto.class)).toList();
        return categoryDtoList;
    }

    @Override
    public List<CategoryResponse> getActiveCategory() {
        
        List<Category> categories= categoryRepo.findByIsActiveTrue(); 

        List<CategoryResponse> categoryList =   categories.stream().map(cat -> mapper.map(cat, CategoryResponse.class)).toList();

        return categoryList;
    }
}

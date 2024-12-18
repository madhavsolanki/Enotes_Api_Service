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
import java.util.Optional;

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

    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepo.findByIsDeletedFalse();

        List<CategoryDto> categoryDtoList =  categories.stream().map(cat->mapper.map(cat, CategoryDto.class)).toList();
        return categoryDtoList;
    }

    @Override
    public List<CategoryResponse> getActiveCategory() {
        
        List<Category> categories= categoryRepo.findByIsActiveTrueAndIsDeletedFalse(); 

        List<CategoryResponse> categoryList =   categories.stream().map(cat -> mapper.map(cat, CategoryResponse.class)).toList();

        return categoryList;
    }

    @Override
    public CategoryDto getCategoryById(Integer id) {
        Optional<Category> findByCategory = categoryRepo.findByIdAndIsDeletedFalse(id);

        if (findByCategory.isPresent()) {
            Category category = findByCategory.get();
            return mapper.map(category, CategoryDto.class);
        }
        return null;
    }

    @Override
    public Boolean deleteCategory(Integer id) {

        Optional<Category> fidByCategory = categoryRepo.findById(id); 

        if (fidByCategory.isPresent()) {
            Category category = fidByCategory.get();
            category.setIsDeleted(true);
            categoryRepo.save(category);

            return true;
        }

        return false;
    }
}

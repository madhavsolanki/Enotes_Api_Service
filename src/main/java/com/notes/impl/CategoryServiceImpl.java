package com.notes.impl;

import com.notes.dto.CategoryDto;
import com.notes.dto.CategoryResponse;
import com.notes.entity.Category;
import com.notes.exception.ResourceNotFoundException;
import com.notes.repository.CategoryRepository;
import com.notes.service.CategoryService;
import com.notes.utils.Validation;

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
    
    @Autowired
    private Validation validation;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {

        // VAlidation Checking
        validation.categoryValidation(categoryDto);

        // Using Model Mapper
        Category category =  mapper.map(categoryDto, Category.class);
        
        if (ObjectUtils.isEmpty(category.getId())) {
            category.setIsDeleted(false);
            // category.setCreatedBy(1);
            // category.setCreatedOn(new Date());    
        }
        else{
            updateCategory(category);
        }

        Category savedCategory = categoryRepo.save(category);
        if (ObjectUtils.isEmpty(savedCategory)){
            return false;
        }
        return true;

    }

    private void updateCategory(Category category){
        Optional<Category> findById = categoryRepo.findById(category.getId());

        if (findById.isPresent()) {
            Category existCategory = findById.get();
            category.setCreatedBy(existCategory.getCreatedBy());
            category.setCreatedOn(existCategory.getCreatedOn());
            category.setIsDeleted(existCategory.getIsDeleted());

            // category.setUpdatedBy(1);
            // category.setUpdatedOn(new Date());
        }
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
    public CategoryDto getCategoryById(Integer id) throws Exception {
        
        Category category = categoryRepo.findByIdAndIsDeletedFalse(id)
        .orElseThrow(()-> new ResourceNotFoundException("Category Not Found with id: "+id));

        if (!ObjectUtils.isEmpty(category)) {
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

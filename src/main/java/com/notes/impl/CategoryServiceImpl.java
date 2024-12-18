package com.notes.impl;

import com.notes.entity.Category;
import com.notes.repository.CategoryRepository;
import com.notes.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Override
    public Boolean saveCategory(Category category) {
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
    public List<Category> getAllCategory() {
        List<Category> categories = categoryRepo.findAll();
        return categories;
    }
}

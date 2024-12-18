package com.notes.controller;

import com.notes.entity.Category;
import com.notes.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody Category category){
        Boolean saveCategory = categoryService.saveCategory(category);

        if (saveCategory) {
            return new ResponseEntity<>("Category Saved", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Failed to save Category", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory(){
        List<Category> allCategory = categoryService.getAllCategory();

        if (CollectionUtils.isEmpty(allCategory)){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }

}

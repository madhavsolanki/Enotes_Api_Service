package com.notes.controller;

import com.notes.dto.CategoryDto;
import com.notes.dto.CategoryResponse;
import com.notes.exception.ResourceNotFoundException;
import com.notes.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto){
        Boolean saveCategory = categoryService.saveCategory(categoryDto);

        if (saveCategory) {
            return new ResponseEntity<>("Category Saved", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Failed to save Category", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCategory(){
        List<CategoryDto> allCategory = categoryService.getAllCategory();

        if (CollectionUtils.isEmpty(allCategory)){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveCategory(){
        List<CategoryResponse> allCategory = categoryService.getActiveCategory();

        if (CollectionUtils.isEmpty(allCategory)){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception{

        CategoryDto categoryDto = categoryService.getCategoryById(id);

        if (ObjectUtils.isEmpty(categoryDto)) {
            return new ResponseEntity<>("Category Not Found with id: "+id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
            
        

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id){

        Boolean deleted = categoryService.deleteCategory(id);

        if (deleted) {
            return new ResponseEntity<>("Category deleted successfully of Id: "+id, HttpStatus.OK);
        }
        return new ResponseEntity<>("Category Not Deleted", HttpStatus.INTERNAL_SERVER_ERROR);

    }

}

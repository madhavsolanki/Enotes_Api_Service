package com.notes.repository;

import com.notes.entity.Category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

  List<Category> findByIsActiveTrueAndIsDeletedFalse();

  Optional<Category> findByIdAndIsDeletedFalse(Integer id);

  List<Category> findByIsDeletedFalse();
}

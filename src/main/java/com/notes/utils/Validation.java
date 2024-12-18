package com.notes.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.notes.dto.CategoryDto;
import com.notes.exception.ValidationException;

@Component
public class Validation {

  public void categoryValidation(CategoryDto categoryDto) {

    Map<String, Object> error = new LinkedHashMap<>();

    if (ObjectUtils.isEmpty(categoryDto)) {
      throw new IllegalArgumentException("category object/JSON cannot be null or empty");
    } else {

      // Validation of Name Field
      if (ObjectUtils.isEmpty(categoryDto.getName())) {
        error.put("name", "name field id null or empty");
      } else {
        if (categoryDto.getName().length() < 3) {
          error.put("name", "name length minimum 3");
        }

        if (categoryDto.getName().length() > 100) {
          error.put("name", "name length maximum is 100");
        }
      }

      // Validation Description Field
      if (ObjectUtils.isEmpty(categoryDto.getDescription())) {
        error.put("description", "description field id null or empty");
      }

      // Validating isActive Field
      if (ObjectUtils.isEmpty(categoryDto.getIsActive())) {
        error.put("isActive", "isActive field is empty or null");
      } else {

        if (categoryDto.getIsActive() != Boolean.TRUE.booleanValue() &&
            categoryDto.getIsActive() != Boolean.FALSE.booleanValue()) {
          error.put("isActive", "invalid value isActive field is empty or null");
        }
      }

    }

    if (!error.isEmpty()) {
      throw new ValidationException(error);

    }

  }

}

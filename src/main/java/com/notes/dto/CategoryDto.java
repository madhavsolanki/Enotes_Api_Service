package com.notes.dto;

import java.util.Date;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

  private Integer id;

  
  private String name;

 
  private String description;


  private Boolean isActive;

  private Integer createdBy;

  private Date createdOn;

  private Integer updatedBy;

  private Date updatedOn;
  
}

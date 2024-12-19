package com.notes.dto;

import java.util.Date;

import com.notes.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotesDto {

  private Integer id;

  private String title;

  private String description;

  private Category category;

  private Boolean isActive;

  private Integer createdBy;

  private Date createdOn;

  private Integer updatedBy;

  private Date updatedOn;

}

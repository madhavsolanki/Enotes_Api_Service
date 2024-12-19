package com.notes.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.notes.dto.NotesDto;
import com.notes.entity.Category;
import com.notes.entity.Notes;
import com.notes.exception.ResourceNotFoundException;
import com.notes.repository.CategoryRepository;
import com.notes.repository.NotesRepository;
import com.notes.service.NotesService;

@Service
public class NotesServiceImpl implements NotesService {

  @Autowired
  private NotesRepository notesRepo;

  @Autowired
  private ModelMapper mapper;

  @Autowired
  private CategoryRepository categoryRepo;

  @Override
  public Boolean saveNotes(NotesDto notesDto) throws Exception {

    // category validation
    checkCategoryExist(notesDto.getCategory());

    // Notes notes = mapper.map(notesDto, Notes.class);

    // Notes saveNotes = notesRepo.save(notes);
    // if (!ObjectUtils.isEmpty(saveNotes)) {
    // return true;
    // }
    // return false;

    // Validate the Category
    if (notesDto.getCategory() == null || notesDto.getCategory().getId() == null) {
      throw new IllegalArgumentException("Category or Category ID cannot be null");
    }
    checkCategoryExist(notesDto.getCategory());

    // Map and Save the Notes entity
    Notes notes = mapper.map(notesDto, Notes.class);
    Notes savedNotes = notesRepo.save(notes);

    // Return true if the entity was saved successfully
    return savedNotes != null && savedNotes.getId() != null;
  }

  private void checkCategoryExist(Category category) throws Exception {
    categoryRepo.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException("category id invalid"));

  }

  @Override
  public List<NotesDto> getAllNotes() {

    return notesRepo.findAll().stream().map(note -> mapper.map(note, NotesDto.class)).toList();

  }

}

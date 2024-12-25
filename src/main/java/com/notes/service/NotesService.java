package com.notes.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.notes.dto.NotesDto;
import com.notes.exception.ResourceNotFoundException;

public interface NotesService {
  
  public Boolean saveNotes(String notes, MultipartFile file) throws ResourceNotFoundException, Exception;

  public List<NotesDto> getAllNotes();

}

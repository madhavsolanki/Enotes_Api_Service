package com.notes.service;

import java.util.List;

import com.notes.dto.NotesDto;
import com.notes.exception.ResourceNotFoundException;

public interface NotesService {
  
  public Boolean saveNotes(NotesDto notesDto) throws ResourceNotFoundException, Exception;

  public List<NotesDto> getAllNotes();

}

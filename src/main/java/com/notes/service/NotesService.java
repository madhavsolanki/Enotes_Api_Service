package com.notes.service;

import java.util.List;

import com.notes.dto.NotesDto;

public interface NotesService {
  
  public Boolean saveNotes(NotesDto notesDto);

  public List<NotesDto> getAllNotes();

}

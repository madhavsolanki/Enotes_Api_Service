package com.notes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notes.service.NotesService;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
  
  @Autowired
  private NotesService notesService;

  

}

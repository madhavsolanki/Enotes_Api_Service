package com.notes.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notes.dto.NotesDto;
import com.notes.entity.Category;
import com.notes.entity.FileDetails;
import com.notes.entity.Notes;
import com.notes.exception.ResourceNotFoundException;
import com.notes.repository.CategoryRepository;
import com.notes.repository.FileRepository;
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

  @Value("${file.upload.path}")
  private String uploadpath;

  @Autowired
  private FileRepository fileRepo;

  @Override
  public Boolean saveNotes(String notes, MultipartFile file) throws Exception {

    // Take Notes in String Format and Convert it into Dto
    
		ObjectMapper ob = new ObjectMapper();
		NotesDto notesDto = ob.readValue(notes, NotesDto.class);

		// category validation
		checkCategoryExist(notesDto.getCategory());

		Notes notesMap = mapper.map(notesDto, Notes.class);

		FileDetails fileDtls = saveFileDetails(file);

		if (!ObjectUtils.isEmpty(fileDtls)) {
			notesMap.setFileDetails(fileDtls);
		} else {
			notesMap.setFileDetails(null);
		}

		Notes saveNotes = notesRepo.save(notesMap);
		if (!ObjectUtils.isEmpty(saveNotes)) {
			return true;
		}
		return false;
  }

  private FileDetails saveFileDetails(MultipartFile file) throws IOException {

    if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {
     

      String originalFileName = file.getOriginalFilename();
      String extension = FilenameUtils.getExtension(originalFileName);

      List<String> extensionAllow = Arrays.asList("pdf","xlsx","jpg","png","docx","jpeg");
      if (!extensionAllow.contains(extension)) {
        throw new IllegalArgumentException("Invalid File Format, Upload Only .pdf, .xlsx, .jpg, .png, .docx, .jpeg");
      }

       

    

      // Genrate Random Number for file names for storing in db
      String rndString = UUID.randomUUID().toString();
      String uploadFileName = rndString+"."+extension;
      
  

      File saveFile = new File(uploadpath);
      
      // Create Folder for Storing File 
      if (!saveFile.exists()) {
          saveFile.mkdir();
      }

      // ex- path: enotesapiservice/notes/java.pdf
      String storePath = uploadpath.concat(uploadFileName);

   

      // upload file
      long upload = Files.copy(file.getInputStream(),Paths.get(storePath));
      
      if (upload != 0) {
        // Creating Object Of File Details Entity
        FileDetails fileDtls = new FileDetails();
        fileDtls.setOriginalFileName(originalFileName);

        fileDtls.setDisplayFileName(getDisplayFileName(originalFileName));

        fileDtls.setUploadFileName(uploadFileName);

        fileDtls.setFileSize(file.getSize());
        fileDtls.setPath(storePath);

        FileDetails savedFileDtls =  fileRepo.save(fileDtls);
        return savedFileDtls;
      }
    }
    return null;
  }

  private String getDisplayFileName(String originalFileName) {

    // Get Extension If the Uploaded File
    String extension = FilenameUtils.getExtension(originalFileName);

    // Remove Extension of the Uploaded File
    String fileName = FilenameUtils.removeExtension(originalFileName);

    // If Uploaded File Name length > 8 take only 0 to 8 charcters for storing file name
    if (fileName.length() > 8) {
      fileName = fileName.substring(0,7);
    }
    // Return the file with adding extension to it
    fileName =  fileName+"."+extension;

    // Return the Processed File
    return fileName;
  }

  private void checkCategoryExist(Category category) throws Exception {
    categoryRepo.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException("category id invalid"));
  }

  @Override
  public List<NotesDto> getAllNotes() {

    return notesRepo.findAll().stream().map(note -> mapper.map(note, NotesDto.class)).toList();

  }

}

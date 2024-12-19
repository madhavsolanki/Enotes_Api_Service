package com.notes.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionhHandler {
  
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(Exception e){

    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  // @ExceptionHandler(MethodArgumentNotValidException.class)
  // public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
  //   List<ObjectError> allErrors = e.getBindingResult().getAllErrors();

  //   Map<String, Object> error = new LinkedHashMap<>();
  //   allErrors.stream().forEach(er -> {
  //     String msg = er.getDefaultMessage();
  //     String field = ((FieldError) (er)).getField();

  //     error.put(field, msg);
  //   });

  //   return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  // }

  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<?> handleNullPointerException(Exception e){

    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> handleResourceNotFoundException(Exception e){

    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }


  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<?> handleValidationException(ValidationException e){

    return new ResponseEntity<>(e.getErrors(), HttpStatus.BAD_REQUEST);
  }
}

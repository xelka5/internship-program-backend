package com.tusofia.internshipprogram.exception.handler;


import com.tusofia.internshipprogram.dto.error.ErrorDto;
import com.tusofia.internshipprogram.exception.AlreadyAppliedException;
import com.tusofia.internshipprogram.exception.EntityNotFoundException;
import com.tusofia.internshipprogram.exception.InsufficientRightsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({ EntityNotFoundException.class })
  public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
    ErrorDto errorDto = new ErrorDto(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), null);

    return new ResponseEntity<>(errorDto, new HttpHeaders(), errorDto.getStatus());
  }

  @ExceptionHandler({ AlreadyAppliedException.class })
  public ResponseEntity<Object> handleAlreadyAppliedException(AlreadyAppliedException ex) {
    ErrorDto errorDto = new ErrorDto(HttpStatus.CONFLICT, ex.getLocalizedMessage(), null);

    return new ResponseEntity<>(errorDto, new HttpHeaders(), errorDto.getStatus());
  }

  @ExceptionHandler({ InsufficientRightsException.class })
  public ResponseEntity<Object> handleInsufficientRightsException(InsufficientRightsException ex) {
    ErrorDto errorDto = new ErrorDto(HttpStatus.FORBIDDEN, ex.getLocalizedMessage(), null);

    return new ResponseEntity<>(errorDto, new HttpHeaders(), errorDto.getStatus());
  }

  @ExceptionHandler({ Exception.class })
  public ResponseEntity<Object> handleException(Exception ex) {
    ErrorDto errorDto = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), null);

    return new ResponseEntity<>(errorDto, new HttpHeaders(), errorDto.getStatus());
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers,
                                                                HttpStatus status, WebRequest request) {

    List<String> errors = new ArrayList<>();

    exception.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.add(fieldName.concat(": ").concat(errorMessage));
    });

    ErrorDto errorDto = new ErrorDto(HttpStatus.UNPROCESSABLE_ENTITY, "Field validation failed", errors);

    return new ResponseEntity<>(errorDto, new HttpHeaders(), errorDto.getStatus());
  }

}

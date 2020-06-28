package com.tusofia.internshipprogram.exception.handler;


import com.tusofia.internshipprogram.dto.error.ErrorDto;
import com.tusofia.internshipprogram.exception.AlreadyAppliedException;
import com.tusofia.internshipprogram.exception.EntityNotFoundException;
import com.tusofia.internshipprogram.exception.InsufficientRightsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
}

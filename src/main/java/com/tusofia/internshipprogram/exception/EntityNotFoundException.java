package com.tusofia.internshipprogram.exception;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(String message) {
    super(message);
  }

  public EntityNotFoundException(String message, Throwable e) {
    super(message, e);
  }

  public EntityNotFoundException(Throwable e) {
    super(e);
  }

}

package com.tusofia.internshipprogram.exception;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String message) {
    super(message);
  }

  public UserNotFoundException(String message, Throwable e) {
    super(message, e);
  }

  public UserNotFoundException(Throwable e) {
    super(e);
  }

}

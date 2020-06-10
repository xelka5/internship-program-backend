package com.tusofia.internshipprogram.exception;

public class InsufficientRightsException extends RuntimeException {

  public InsufficientRightsException(String message) {
    super(message);
  }

  public InsufficientRightsException(String message, Throwable e) {
    super(message, e);
  }

  public InsufficientRightsException(Throwable e) {
    super(e);
  }

}

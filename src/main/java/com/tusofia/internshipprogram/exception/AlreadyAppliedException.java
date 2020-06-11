package com.tusofia.internshipprogram.exception;

public class AlreadyAppliedException extends RuntimeException {

  public AlreadyAppliedException(String message) {
    super(message);
  }

  public AlreadyAppliedException(String message, Throwable e) {
    super(message, e);
  }

  public AlreadyAppliedException(Throwable e) {
    super(e);
  }
}

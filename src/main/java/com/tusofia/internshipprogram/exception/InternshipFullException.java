package com.tusofia.internshipprogram.exception;

public class InternshipFullException extends RuntimeException {

  public InternshipFullException(String message) {
    super(message);
  }

  public InternshipFullException(String message, Throwable e) {
    super(message, e);
  }

  public InternshipFullException(Throwable e) {
    super(e);
  }
}

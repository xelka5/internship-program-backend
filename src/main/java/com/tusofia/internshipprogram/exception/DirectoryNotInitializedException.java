package com.tusofia.internshipprogram.exception;

public class DirectoryNotInitializedException extends RuntimeException {

  public DirectoryNotInitializedException(String message) {
    super(message);
  }

  public DirectoryNotInitializedException(String message, Throwable e) {
    super(message, e);
  }

  public DirectoryNotInitializedException(Throwable e) {
    super(e);
  }

}
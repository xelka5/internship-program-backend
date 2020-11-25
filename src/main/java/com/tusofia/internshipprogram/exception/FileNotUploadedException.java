package com.tusofia.internshipprogram.exception;

public class FileNotUploadedException extends RuntimeException {

  public FileNotUploadedException(String message) {
    super(message);
  }

  public FileNotUploadedException(String message, Throwable e) {
    super(message, e);
  }

  public FileNotUploadedException(Throwable e) {
    super(e);
  }

}

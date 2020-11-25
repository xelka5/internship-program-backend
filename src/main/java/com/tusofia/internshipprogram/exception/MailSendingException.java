package com.tusofia.internshipprogram.exception;

public class MailSendingException extends RuntimeException {

  public MailSendingException(String message) {
    super(message);
  }

  public MailSendingException(String message, Throwable e) {
    super(message, e);
  }

  public MailSendingException(Throwable e) {
    super(e);
  }
}
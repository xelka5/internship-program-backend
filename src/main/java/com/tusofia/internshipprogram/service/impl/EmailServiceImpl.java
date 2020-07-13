package com.tusofia.internshipprogram.service.impl;

import com.tusofia.internshipprogram.mail.EmailMessageHolder;
import com.tusofia.internshipprogram.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

  private JavaMailSender mailSender;

  @Autowired
  public EmailServiceImpl(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public <T extends EmailMessageHolder> void sendMessage(T sendingObject) {
    MimeMessage mimeMessage = sendingObject.createEmailMessageContent(mailSender.createMimeMessage());

    mailSender.send(mimeMessage);
  }
}

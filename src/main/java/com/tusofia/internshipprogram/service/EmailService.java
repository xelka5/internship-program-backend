package com.tusofia.internshipprogram.service;

import com.tusofia.internshipprogram.mail.EmailMessageHolder;

public interface EmailService {

  public <T extends EmailMessageHolder> void sendMessage(T sendingObject);
}

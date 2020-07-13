package com.tusofia.internshipprogram.mail;

import javax.mail.internet.MimeMessage;

public abstract class EmailMessageHolder {

  public abstract MimeMessage createEmailMessageContent(MimeMessage sendingInformation);
}

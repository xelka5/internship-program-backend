package com.tusofia.internshipprogram.mail;

import javax.mail.internet.MimeMessage;

/**
 * Base email message holder class used across message builder components.
 *
 * @author DCvetkov
 * @since 2020
 */
public abstract class EmailMessageHolder {

  public abstract MimeMessage createEmailMessageContent(MimeMessage sendingInformation);
}

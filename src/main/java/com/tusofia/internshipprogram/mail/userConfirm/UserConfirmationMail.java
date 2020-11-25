package com.tusofia.internshipprogram.mail.userConfirm;

import com.google.common.base.Charsets;
import com.tusofia.internshipprogram.exception.MailSendingException;
import com.tusofia.internshipprogram.mail.EmailMessageHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static com.tusofia.internshipprogram.util.GlobalConstants.COULD_NOT_BUILD_EMAIL_MESSAGE;
import static com.tusofia.internshipprogram.util.GlobalUtility.getEmailBody;

/**
 * Application response email message builder.
 *
 * @author DCvetkov
 * @since 2020
 */
@Slf4j
public class UserConfirmationMail extends EmailMessageHolder {

  private static final String USER_CONFIRMATION_BODY_FILE = "emailTemplates/user-confirmation-template.txt";

  private static final String SUBJECT = "User confirmation";
  private static final String FROM_EMAIL = "noreply@internships.com";

  private String sendToUser;
  private String generatedToken;
  private String environmentUrl;

  public UserConfirmationMail(String sendToUser, String generatedToken, String environmentUrl) {
    this.sendToUser = sendToUser;
    this.generatedToken = generatedToken;
    this.environmentUrl = environmentUrl;
  }

  @Override
  public MimeMessage createEmailMessageContent(MimeMessage mimeMessage) {
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, Charsets.UTF_8.name());

    try {
      String emailBody = getEmailBody(USER_CONFIRMATION_BODY_FILE);
      emailBody = emailBody.replace("{{environmentUrl}}", environmentUrl)
                           .replace("{{userEmail}}", sendToUser)
                           .replace("{{generatedToken}}", generatedToken);

      helper.setText(emailBody, true);
      helper.setTo(sendToUser);
      helper.setSubject(SUBJECT);
      helper.setFrom(FROM_EMAIL);

    } catch (MessagingException e) {
      LOGGER.error(COULD_NOT_BUILD_EMAIL_MESSAGE, e);
      throw new MailSendingException(COULD_NOT_BUILD_EMAIL_MESSAGE, e);
    }
    return mimeMessage;
  }

}

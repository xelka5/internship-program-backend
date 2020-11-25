package com.tusofia.internshipprogram.mail.applicationResponse;

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
public class ApplicationResponseMail extends EmailMessageHolder {

  private static final String APPLICATION_RESPONSE_BODY_FILE = "emailTemplates/application-response-template.txt";

  private static final String SUBJECT = "Internship application response";
  private static final String FROM_EMAIL = "noreply@internships.com";

  private String sendToUser;
  private String internshipTitle;
  private String response;

  public ApplicationResponseMail(String sendToUser, String internshipTitle, String response) {
    this.sendToUser = sendToUser;
    this.internshipTitle = internshipTitle;
    this.response = response;
  }

  @Override
  public MimeMessage createEmailMessageContent(MimeMessage mimeMessage) {
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, Charsets.UTF_8.name());

    try {
      String emailBody = getEmailBody(APPLICATION_RESPONSE_BODY_FILE);
      emailBody = emailBody.replace("{{internshipTitle}}", internshipTitle)
                           .replace("{{response}}", response);

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

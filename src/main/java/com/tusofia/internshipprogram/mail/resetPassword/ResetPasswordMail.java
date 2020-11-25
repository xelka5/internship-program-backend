package com.tusofia.internshipprogram.mail.resetPassword;

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
 * Reset password email message builder.
 *
 * @author DCvetkov
 * @since 2020
 */
@Slf4j
public class ResetPasswordMail extends EmailMessageHolder {

  private static final String PASSWORD_RESET_BODY_FILE = "emailTemplates/reset-password-template.txt";

  private static final String SUBJECT = "Password reset code";
  private static final String FROM_EMAIL = "noreply@internships.com";

  private String sendToUser;
  private String resetCode;

  public ResetPasswordMail(String sendToUser, String resetCode) {
    this.sendToUser = sendToUser;
    this.resetCode = resetCode;
  }

  @Override
  public MimeMessage createEmailMessageContent(MimeMessage mimeMessage) {
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, Charsets.UTF_8.name());

    try {
      String emailBody = getEmailBody(PASSWORD_RESET_BODY_FILE);
      emailBody = emailBody.replace("{{resetCode}}", resetCode);

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

package com.tusofia.internshipprogram.mail.internshipDeleted;

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
 * Internship deleted email message builder.
 *
 * @author DCvetkov
 * @since 2020
 */
@Slf4j
public class InternshipDeletedMail extends EmailMessageHolder {

  private static final String INTERNSHIP_DELETED_BODY_FILE = "emailTemplates/internship-deleted-template.txt";

  private static final String SUBJECT = "Internship deleted by force";
  private static final String FROM_EMAIL = "noreply@internships.com";

  private String sendToUser;
  private String companyName;
  private String internshipTitle;

  public InternshipDeletedMail(String sendToUser, String companyName, String internshipTitle) {
    this.sendToUser = sendToUser;
    this.companyName = companyName;
    this.internshipTitle = internshipTitle;
  }

  @Override
  public MimeMessage createEmailMessageContent(MimeMessage mimeMessage) {
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, Charsets.UTF_8.name());

    try {
      String htmlMsg = getEmailBody(INTERNSHIP_DELETED_BODY_FILE);
      htmlMsg = htmlMsg.replace("{{companyName}}", companyName)
                       .replace("{{internshipTitle}}", internshipTitle);

      helper.setText(htmlMsg, true);
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

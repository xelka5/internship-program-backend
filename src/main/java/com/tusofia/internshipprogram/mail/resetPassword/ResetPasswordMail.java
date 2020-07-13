package com.tusofia.internshipprogram.mail.resetPassword;

import com.tusofia.internshipprogram.mail.EmailMessageHolder;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class ResetPasswordMail extends EmailMessageHolder {

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
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

    try {
      String htmlMsg = getHtmlContent();
      htmlMsg = htmlMsg.replace("{{resetCode}}", resetCode);

      helper.setText(htmlMsg, true);
      helper.setTo(sendToUser);
      helper.setSubject(SUBJECT);
      helper.setFrom(FROM_EMAIL);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    return mimeMessage;
  }

  private String getHtmlContent() {
    return "<!DOCTYPE html><html lang=\"en\"><head> <meta charset=\"utf-8\"> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\"> <script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin=\"anonymous\"></script> <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\" integrity=\"sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q\" crossorigin=\"anonymous\"></script> <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" integrity=\"sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl\" crossorigin=\"anonymous\"></script> <style>.card{min-height: 400px;}.card-header{color: white; background-color: #0098a1; border: none;}</style></head><body><div class=\"container\"> <div class=\"card mt-5\"> <h5 class=\"card-header\">Password reset</h5> <div class=\"card-body\"> <h5 class=\"card-title\">Your new reset code: <span>{{resetCode}}</span></h5> </div></div></div></body></html>";
  }
}

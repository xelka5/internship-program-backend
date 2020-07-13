package com.tusofia.internshipprogram.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class ApplicationConfig {

  @Value("${resources.profileImagesDirectory}")
  private String profileImagesDirectory;

  @Value("${resources.finalReportsDirectory}")
  private String finalReportsDirectory;

  @Value("${resources.environment-url:http://localhost:4200}")
  private String environmentUrl;

  public String getProfileImagesDirectory() {
    return profileImagesDirectory;
  }

  public String getFinalReportsDirectory() {
    return finalReportsDirectory;
  }

  public String getEnvironmentUrl() {
    return environmentUrl;
  }

  @Bean
  public JavaMailSender gmailMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);

    mailSender.setUsername("mailsender.internships@gmail.com");
    mailSender.setPassword("swdjwnftpmjcqacn");

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "false");

    return mailSender;
  }
}

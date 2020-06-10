package com.tusofia.internshipprogram.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  @Value("${resources.profileImagesDirectory}")
  private String profileImagesDirectory;

  public String getProfileImagesDirectory() {
    return profileImagesDirectory;
  }
}

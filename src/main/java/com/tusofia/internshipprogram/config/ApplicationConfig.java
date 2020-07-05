package com.tusofia.internshipprogram.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  @Value("${resources.profileImagesDirectory}")
  private String profileImagesDirectory;

  @Value("${resources.finalReportsDirectory}")
  private String finalReportsDirectory;

  public String getProfileImagesDirectory() {
    return profileImagesDirectory;
  }

  public String getFinalReportsDirectory() {
    return finalReportsDirectory;
  }
}

package com.tusofia.internshipprogram.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Custom application configuration.
 *
 * @author DCvetkov
 * @since 2020
 */
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

}

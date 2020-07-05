package com.tusofia.internshipprogram.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Value("${resources.profileImagesDirectory}")
  private String profileImagesDirectory;

  @Value("${resources.finalReportsDirectory}")
  private String finalReportsDirectory;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/profile-images/**")
            .addResourceLocations("file:./" + profileImagesDirectory + "/")
            .setCachePeriod(31556926);
    registry.addResourceHandler("/final-reports/**")
            .addResourceLocations("file:./" + finalReportsDirectory + "/")
            .setCachePeriod(31556926);
    registry.addResourceHandler("/templates/**")
            .addResourceLocations("classpath:/templates/");
  }
}

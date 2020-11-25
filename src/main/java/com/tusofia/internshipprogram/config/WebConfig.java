package com.tusofia.internshipprogram.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

/**
 * Custom web configuration for expression and resource handling components.
 *
 * @author DCvetkov
 * @since 2020
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Value("${resources.profileImagesDirectory}")
  private String profileImagesDirectory;

  @Value("${resources.finalReportsDirectory}")
  private String finalReportsDirectory;

  /**
   * Adding custom images, reports and template resources to the resource handler registry.
   *
   * @param registry - object managing all resource handler patterns and locations
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/profile-images/**")
            .addResourceLocations("file:./" + profileImagesDirectory + "/")
            .setCachePeriod((int) TimeUnit.DAYS.toSeconds(1));
    registry.addResourceHandler("/final-reports/**")
            .addResourceLocations("file:./" + finalReportsDirectory + "/")
            .setCachePeriod((int) TimeUnit.DAYS.toSeconds(1));
    registry.addResourceHandler("/reportTemplates/**")
            .addResourceLocations("classpath:/reportTemplates/");
    registry.addResourceHandler("/images/**")
            .addResourceLocations("classpath:/images/");
    registry.addResourceHandler("/swagger-ui.html**")
            .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
    registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

  /**
   * Initiating application context to the oauth expression manager
   *
   * @param applicationContext - context of the whole spring boot application container
   * @return {@link OAuth2WebSecurityExpressionHandler}
   */
  @Bean
  public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
    OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
    expressionHandler.setApplicationContext(applicationContext);
    return expressionHandler;
  }
}

package com.tusofia.internshipprogram.config;


import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Default swagger documentation settings. This configuration is disabled for the prod environment.
 *
 * @author DCvetkov
 * @since 2020
 */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
@Profile("!prod")
public class SwaggerConfig {

  private static final String INTERNSHIP_PROGRAM_GROUP = "Internship program API";
  private static final String OAUTH2_GROUP_NAME = "OAuth 2.0 protocol API";

  private static final Predicate<String> INTERNSHIP_PROGRAM_API = PathSelectors.ant("/api/**");
  private static final Predicate<String> OAUTH2_API = PathSelectors.ant("/oauth/**");

  @Autowired
  private BuildProperties buildProperties;

  @Autowired
  private SecurityScheme securityScheme;

  @Autowired
  private SecurityContext securityContext;

  /**
   * Service related API swagger documentation processor.
   *
   * @return {@link Docket}
   */
  @Bean
  public Docket internshipProgramApi() {
    return new Docket(DocumentationType.SWAGGER_2)
            .groupName(INTERNSHIP_PROGRAM_GROUP)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(INTERNSHIP_PROGRAM_API)
            .build()
            .securitySchemes(Lists.newArrayList(securityScheme))
            .securityContexts(Lists.newArrayList(securityContext));
  }

  /**
   * Oauth2 related API swagger documentation processor.
   *
   * @return {@link Docket}
   */
  @Bean
  public Docket authenticationApi() {
    return new Docket(DocumentationType.SWAGGER_2)
            .groupName(OAUTH2_GROUP_NAME)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(OAUTH2_API)
            .build();
  }

  /**
   * Base information about the service the for swagger ui.
   *
   * @return {@link ApiInfo}
   */
  private ApiInfo apiInfo() {
    Contact contact = new Contact("Dusan Cvetkov", "https://github.com/xelka5", "xelcina@gmail.com");

    return new ApiInfoBuilder()
            .title("Internship program REST API")
            .description("List of available API served by Internship program application")
            .version(buildProperties.getVersion())
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
            .contact(contact)
            .build();
  }
}

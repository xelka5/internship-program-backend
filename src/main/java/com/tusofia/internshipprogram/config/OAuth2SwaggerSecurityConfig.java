package com.tusofia.internshipprogram.config;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import java.util.List;

import static com.tusofia.internshipprogram.util.GlobalConstants.READ_SCOPE;
import static com.tusofia.internshipprogram.util.GlobalConstants.WRITE_SCOPE;

/**
 * Configuration allowing security based requests from the swagger engine.
 *
 * @author DCvetkov
 * @since 2020
 */
@Configuration
public class OAuth2SwaggerSecurityConfig {

  private static final String SECURITY_SCHEME_NAME = "swagger_oauth2_schema";
  private static final String TOKEN_URL = "http://localhost:8080/oauth/token";

  private static final String READ_OPERATIONS = "Read operations";
  private static final String WRITE_OPERATIONS = "Write operations";

  @Value("${oauthData.clientId}")
  private String clientId;

  @Value("${oauthData.clientSecret}")
  private String clientSecret;

  @Bean
  public SecurityConfiguration securityInfo() {
    return SecurityConfigurationBuilder.builder()
            .clientId(clientId)
            .clientSecret(clientSecret)
            .scopeSeparator(" ")
            .build();
  }

  @Bean
  public SecurityScheme securityScheme() {
    GrantType grantType = new ResourceOwnerPasswordCredentialsGrant(TOKEN_URL);

    SecurityScheme oauth = new OAuthBuilder().name(SECURITY_SCHEME_NAME)
            .grantTypes(Lists.newArrayList(grantType))
            .scopes(Lists.newArrayList(scopes()))
            .build();

    return oauth;
  }

  @Bean
  public SecurityContext securityContext() {
    return SecurityContext.builder()
            .securityReferences(securityReferences())
            .forPaths(Predicates.alwaysTrue())
            .build();
  }

  private List<SecurityReference> securityReferences() {
    return Lists.newArrayList(new SecurityReference(SECURITY_SCHEME_NAME, scopes()));
  }

  private AuthorizationScope[] scopes() {
    AuthorizationScope[] scopes = {
            new AuthorizationScope(READ_SCOPE, READ_OPERATIONS),
            new AuthorizationScope(WRITE_SCOPE, WRITE_OPERATIONS)
    };

    return scopes;
  }

}

package com.tusofia.internshipprogram.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;

/**
 * Basic resource server configuration with oauth2 protocol.
 *
 * @author DCvetkov
 * @since 2020
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

  @Autowired
  private OAuth2WebSecurityExpressionHandler expressionHandler;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .antMatchers("/oauth/token", "/")
      .permitAll()
      .and()
      .requestMatchers()
      .antMatchers("/api/**")
      .and()
      .authorizeRequests()
      // permit registration and reset password endpoints
      .antMatchers("/api/users/registration/**", "/api/users/reset-password/**", "/api/system/**")
      .permitAll()
      // authorize admin role
      .antMatchers("/api/**/admin/**")
      .access("@authenticationUtils.checkAdmin(authentication,request)")
      // authorize employer role
      .antMatchers("/api/**/employer/**")
      .access("@authenticationUtils.checkEmployer(authentication,request)")
      // authorize any request not included above
      .antMatchers("/api/**")
      .access("@authenticationUtils.isUserActiveAndAllowed(authentication,request)")
      // authenticate all requests
      .anyRequest()
      .authenticated();
  }

  @Override
  public void configure(final ResourceServerSecurityConfigurer resources) throws Exception {
    resources.expressionHandler(expressionHandler);
  }

}

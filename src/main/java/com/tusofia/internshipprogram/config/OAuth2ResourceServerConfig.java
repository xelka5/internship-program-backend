package com.tusofia.internshipprogram.config;

import com.tusofia.internshipprogram.enumeration.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

  @Autowired
  private OAuth2WebSecurityExpressionHandler expressionHandler;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
      .cors().disable()
      .authorizeRequests()
      .antMatchers("/oauth/token")
      .permitAll()
      .antMatchers("/")
      .permitAll()
      .and()
      .requestMatchers()
      .antMatchers("/api/**")
      .and()
      .authorizeRequests()
      .antMatchers("/api/users/registration/**", "/api/users/reset-password/**")
      .permitAll()
      .antMatchers("/api/**/admin/**")
      .access("@authenticationUtils.checkAdmin(authentication,request)")
      .antMatchers("/api/**/employer/**")
      .access("@authenticationUtils.checkEmployer(authentication,request)")
      .anyRequest()
      .authenticated();
  }

  @Override
  public void configure(final ResourceServerSecurityConfigurer resources) throws Exception {
    resources.expressionHandler(expressionHandler);
  }

}

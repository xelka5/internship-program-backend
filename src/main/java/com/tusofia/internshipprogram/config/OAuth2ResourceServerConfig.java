package com.tusofia.internshipprogram.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

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
      .antMatchers("/api/users/registration")
      .permitAll()
      .antMatchers("/api/users")
      .hasAuthority("ADMIN")
      .anyRequest()
      .authenticated();
  }


}

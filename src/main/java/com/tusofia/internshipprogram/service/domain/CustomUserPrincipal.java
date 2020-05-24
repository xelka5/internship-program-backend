package com.tusofia.internshipprogram.service.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class CustomUserPrincipal extends User {

  private String email;

  public CustomUserPrincipal(String username, String password,
                             Collection<? extends GrantedAuthority> authorities, String email) {
    super(username, password, authorities);
    this.email = email;
  }
}

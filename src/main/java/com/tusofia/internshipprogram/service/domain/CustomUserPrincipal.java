package com.tusofia.internshipprogram.service.domain;

import com.tusofia.internshipprogram.enumeration.UserStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class CustomUserPrincipal extends User {

  private String email;
  private UserStatus userStatus;
  private Boolean userAllowed;

  public CustomUserPrincipal(String username, String password, UserStatus userStatus,
                             Collection<? extends GrantedAuthority> authorities, String email, Boolean userAllowed) {
    super(username, password, authorities);
    this.email = email;
    this.userStatus = userStatus;
    this.userAllowed = userAllowed;
  }
}

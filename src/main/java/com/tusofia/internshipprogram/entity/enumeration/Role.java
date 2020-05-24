package com.tusofia.internshipprogram.entity.enumeration;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  INTERN, EMPLOYER, ADMIN;

  @Override
  public String getAuthority() {
    return this.name();
  }

}
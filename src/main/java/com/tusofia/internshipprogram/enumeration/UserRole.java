package com.tusofia.internshipprogram.enumeration;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
  INTERN, EMPLOYER, ADMIN, PENDING, BLOCKED;

  @Override
  public String getAuthority() {
    return this.name();
  }

}
package com.tusofia.internshipprogram.enumeration;

import com.tusofia.internshipprogram.entity.user.User;

public enum UserStatus {
  ACTIVE, PENDING_REGISTRATION, BLOCKED, INACTIVE;

  public static void setInitialUserStatusIfNotExists(User user, UserStatus initStatus) {
    if(user.getStatus() == null) {
      user.setStatus(initStatus);
    }
  }
}

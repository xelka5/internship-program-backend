package com.tusofia.internshipprogram.dto;

import com.tusofia.internshipprogram.entity.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

  private String username;
  private String password;
  private String email;
  private Role role;
}

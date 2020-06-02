package com.tusofia.internshipprogram.dto.registration;

import com.tusofia.internshipprogram.entity.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {

  @Valid
  private AccountDto account;

  @NotNull
  private Role role;

  private Object userDetails;
}

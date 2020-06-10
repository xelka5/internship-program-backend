package com.tusofia.internshipprogram.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tusofia.internshipprogram.enumeration.UserRole;
import com.tusofia.internshipprogram.enumeration.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetailsDto {

  @Valid
  private AccountDto account;

  @NotNull
  private UserRole role;

  private Object userDetails;

  private UserStatus status;
}

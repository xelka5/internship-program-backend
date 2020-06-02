package com.tusofia.internshipprogram.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

  @NotEmpty
  @Size(min = 4, max = 20)
  private String username;

  @NotEmpty
  @Size(min = 8, max = 20)
  private String password;

  @NotEmpty
  @Email
  private String email;
}

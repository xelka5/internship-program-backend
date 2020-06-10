package com.tusofia.internshipprogram.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {

  @NotEmpty
  @Size(min = 4, max = 20)
  private String username;

  private String password;

  @NotEmpty
  @Email
  private String email;

  private String profileImageName;
}

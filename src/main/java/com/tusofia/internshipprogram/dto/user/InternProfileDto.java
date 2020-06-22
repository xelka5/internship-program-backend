package com.tusofia.internshipprogram.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InternProfileDto {

  private String firstName;

  private String lastName;

  private String profileImageName;

  private String email;
}

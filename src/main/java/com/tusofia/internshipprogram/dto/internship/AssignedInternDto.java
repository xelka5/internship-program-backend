package com.tusofia.internshipprogram.dto.internship;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssignedInternDto {

  private String firstName;

  private String lastName;

  private String profileImageName;

  private String email;

  private String university;

}

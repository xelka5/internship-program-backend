package com.tusofia.internshipprogram.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployerUserDetailsDto {

  @NotEmpty
  private String companyName;

  @Min(value = 1, message = "Number of workers should not be less than 1")
  @Max(value = 1000000, message = "Number of workers should not be greater than 1 milion")
  private Integer numberOfWorkers;

  @NotEmpty
  private String historyNotes;

  @NotEmpty
  private String descriptionNotes;
}

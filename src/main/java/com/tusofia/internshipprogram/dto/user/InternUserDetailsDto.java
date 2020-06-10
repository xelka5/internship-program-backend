package com.tusofia.internshipprogram.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InternUserDetailsDto {

  @NotEmpty
  private String firstName;

  @NotEmpty
  private String lastName;

  @Min(value = 16, message = "Age should not be less than 16")
  @Max(value = 65, message = "Age should not be greater than 65")
  private Integer age;

  @NotEmpty
  private String university;

  @NotEmpty
  private String course;

  private Date startDate;

  @NotEmpty
  private String previousEducation;

  @NotEmpty
  private List<String> skills;
}

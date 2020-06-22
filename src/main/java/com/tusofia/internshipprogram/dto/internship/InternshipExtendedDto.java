package com.tusofia.internshipprogram.dto.internship;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tusofia.internshipprogram.dto.user.EmployerProfileDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InternshipExtendedDto {

  private String trackingNumber;

  private String title;

  private String description;

  private Date startDate;

  private String salary;

  private Integer maxNumberOfStudents;

  private EmployerProfileDto employer;
}

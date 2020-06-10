package com.tusofia.internshipprogram.dto.internship;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InternshipDto {

  private String trackingNumber;

  private String title;

  private String description;

  private Date startDate;

  private String salary;

  private Integer maxNumberOfStudents;
}

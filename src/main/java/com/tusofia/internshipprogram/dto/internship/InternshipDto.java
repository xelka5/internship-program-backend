package com.tusofia.internshipprogram.dto.internship;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tusofia.internshipprogram.enumeration.Currency;
import com.tusofia.internshipprogram.enumeration.DurationUnit;
import com.tusofia.internshipprogram.enumeration.InternshipStatus;
import com.tusofia.internshipprogram.enumeration.InternshipType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InternshipDto {

  private String trackingNumber;

  @NotEmpty
  private String title;

  @NotEmpty
  private String description;

  private InternshipStatus status;

  @NotNull
  private Date startDate;

  @NotNull
  private Long duration;

  @NotNull
  private DurationUnit durationUnit;

  @NotNull
  private InternshipType type;

  private Long salary;

  private Currency currency;

  @NotNull
  private Integer maxNumberOfStudents;

  private Long numberOfAssignedStudents;
}

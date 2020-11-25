package com.tusofia.internshipprogram.dto.internship;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tusofia.internshipprogram.dto.user.EmployerProfileDto;
import com.tusofia.internshipprogram.enumeration.Currency;
import com.tusofia.internshipprogram.enumeration.DurationUnit;
import com.tusofia.internshipprogram.enumeration.InternshipType;
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

  private Long duration;

  private DurationUnit durationUnit;

  private InternshipType type;

  private Long salary;

  private Currency currency;

  private EmployerProfileDto employer;

  private boolean internReportPresent;
}

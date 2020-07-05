package com.tusofia.internshipprogram.dto.finalReport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tusofia.internshipprogram.enumeration.FinalReportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FinalReportDto {

  private String reportFileLocation;

  private String reportNotes;

  private String trackingNumber;

  @Enumerated(EnumType.STRING)
  private FinalReportType finalReportType;
}

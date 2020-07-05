package com.tusofia.internshipprogram.dto.finalReport;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FinalReportAdminDto {

  private String internshipTitle;

  private String companyName;

  private List<InternDetailsWithFinalReportsDto> finalReports;
}

package com.tusofia.internshipprogram.dto.finalReport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tusofia.internshipprogram.dto.user.InternProfileDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InternDetailsWithFinalReportsDto {

  private InternProfileDto internProfile;

  private String applicationTrackingNumber;

  private FinalReportDto finalReportIntern;

  private FinalReportDto finalReportEmployer;

}

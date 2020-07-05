package com.tusofia.internshipprogram.dto.finalReport;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateFinalReportResponseDto {

  private boolean isSuccess;

  private String reportTrackingNumber;
}

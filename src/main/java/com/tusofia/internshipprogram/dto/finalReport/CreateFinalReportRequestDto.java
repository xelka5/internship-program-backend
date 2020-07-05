package com.tusofia.internshipprogram.dto.finalReport;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateFinalReportRequestDto {

  @NotEmpty
  private String reportNotes;

  private String applicationTrackingNumber;

  private String internshipTrackingNumber;

}

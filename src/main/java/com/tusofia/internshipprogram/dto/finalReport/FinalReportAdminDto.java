package com.tusofia.internshipprogram.dto.finalReport;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response DTO object for final reports on admin side.
 *
 * @author DCvetkov
 * @since 2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Data transfer object model for carrying information for both intern and employer final reports")
public class FinalReportAdminDto {

  private String internshipTitle;

  private String companyName;

  private List<InternDetailsWithFinalReportsDto> finalReports;
}

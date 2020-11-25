package com.tusofia.internshipprogram.dto.finalReport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tusofia.internshipprogram.dto.user.InternProfileDto;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO object for interns final reports information.
 *
 * @author DCvetkov
 * @since 2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Data transfer object model for carrying information for final reports")
public class InternDetailsWithFinalReportsDto {

  private InternProfileDto internProfile;

  private String applicationTrackingNumber;

  private FinalReportDto finalReportIntern;

  private FinalReportDto finalReportEmployer;

}

package com.tusofia.internshipprogram.dto.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InternReportDto {

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'")
  private Date reportStartDate;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'")
  private Date reportEndDate;

  private String reportDetails;

  private String trackingNumber;

  private String internshipTrackingNumber;
}

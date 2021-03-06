package com.tusofia.internshipprogram.dto.application;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tusofia.internshipprogram.enumeration.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationResponseDto {

  private String responseNotes;

  private String trackingNumber;

  private ApplicationStatus status;

}

package com.tusofia.internshipprogram.dto.application;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tusofia.internshipprogram.dto.internship.InternshipDto;
import com.tusofia.internshipprogram.dto.user.InternProfileDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PendingApplicationDto {

  private InternProfileDto intern;

  private InternshipDto internshipDetails;

  private String details;

  private String trackingNumber;

}

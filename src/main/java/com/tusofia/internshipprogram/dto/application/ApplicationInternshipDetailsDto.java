package com.tusofia.internshipprogram.dto.application;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tusofia.internshipprogram.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationInternshipDetailsDto {

  private String trackingNumber;

  private String title;

  private ApplicationEmployerDetailsDto employer;
}

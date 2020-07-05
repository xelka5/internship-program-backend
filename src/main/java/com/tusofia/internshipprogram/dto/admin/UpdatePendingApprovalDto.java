package com.tusofia.internshipprogram.dto.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tusofia.internshipprogram.enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdatePendingApprovalDto {

  private String userEmail;

  private UserRole newUserStatus;
}

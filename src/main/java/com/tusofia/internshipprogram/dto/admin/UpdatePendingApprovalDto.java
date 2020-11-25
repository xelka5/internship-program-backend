package com.tusofia.internshipprogram.dto.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Request DTO object for approving/rejecting pending registrations.
 *
 * @author DCvetkov
 * @since 2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Data transfer object model for approving/rejecting pending registrations")
public class UpdatePendingApprovalDto {

  @NotEmpty
  private String userEmail;

  @NotNull
  private Boolean userAllowed;
}

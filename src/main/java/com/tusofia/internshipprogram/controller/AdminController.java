package com.tusofia.internshipprogram.controller;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.admin.UpdatePendingApprovalDto;
import com.tusofia.internshipprogram.dto.finalReport.FinalReportAdminDto;
import com.tusofia.internshipprogram.dto.internship.InternshipExtendedDto;
import com.tusofia.internshipprogram.dto.user.UserDetailsDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

/**
 * Api description of admin operations controller.
 *
 * @author DCvetkov
 * @since 2020
 */
@Api(tags={"Admin Endpoints"},  description = "Admin users operations")
public interface AdminController {

  @ApiOperation(value = "Get Finished Internships",
                notes = "Retrieves finished internships for all users")
  List<InternshipExtendedDto> getFinishedInternships();

  @ApiOperation(value = "Get Final Internship Reports",
                notes = "Retrieves all final reports for internship by given internship tracking number")
  FinalReportAdminDto getFinalInternshipReports(
          @ApiParam(value = "Internship tracking number", required = true) String internshipTrackingNumber);

  @ApiOperation(value = "Get Pending Registration Users",
          notes = "Retrieves all employers who are pending registration approval")
  List<UserDetailsDto> getPendingRegistrationUsers();

  @ApiOperation(value = "Update Pending Approval",
          notes = "Approves or rejects employer who is waiting approval")
  BaseResponseDto updatePendingApproval(UpdatePendingApprovalDto updatePendingApprovalDto);
}

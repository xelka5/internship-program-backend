package com.tusofia.internshipprogram.controller;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.application.ApplicationDetailsDto;
import com.tusofia.internshipprogram.dto.application.ApplicationResponseDto;
import com.tusofia.internshipprogram.dto.application.InternshipApplicationDto;
import com.tusofia.internshipprogram.dto.application.PendingApplicationDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Api description of internship application operations controller.
 *
 * @author DCvetkov
 * @since 2020
 */
@Api(tags={"Internship application Endpoints"},  description = "Internship application operations")
public interface ApplicationController {

  @ApiOperation(value = "Get All Intern Applications",
          notes = "Retrieves all internship applications for currently logged intern")
  List<ApplicationDetailsDto> getAllInternApplications(@ApiIgnore Authentication authentication);

  @ApiOperation(value = "Get All Pending Applications",
          notes = "Retrieves all internship applications for currently logged intern")
  List<PendingApplicationDto> getAllPendingApplications(@ApiIgnore Authentication authentication);

  @ApiOperation(value = "Create New Internship Application",
          notes = "Creates new pending internship application by an intern")
  BaseResponseDto addNewApplication(InternshipApplicationDto internshipApplicationDto,
                                    @ApiIgnore Authentication authentication);

  @ApiOperation(value = "Update Application Response",
          notes = "Accepts/Rejects pending application from employer side")
  BaseResponseDto updateApplicationResponse(ApplicationResponseDto applicationResponseDto,
                                            @ApiIgnore Authentication authentication);
}

package com.tusofia.internshipprogram.controller;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.report.InternReportDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.core.Authentication;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Api description of intern report operations controller.
 *
 * @author DCvetkov
 * @since 2020
 */
@Api(tags={"Report Endpoints"},  description = "Report operations")
public interface ReportController {

  @ApiOperation(value = "Get Intern Report",
          notes = "Retrieves single intern report by given report tracking number")
  InternReportDto getInternReport(
          @ApiParam(value = "Report tracking number", required = true) String reportTrackingNumber,
          @ApiIgnore Authentication authentication);

  @ApiOperation(value = "Get Intern Reports For Internship",
          notes = "Retrieves all intern reports for a single internship")
  List<InternReportDto> getInternReportsForInternship(
          @ApiParam(value = "Internship tracking number", required = true) String internshipTrackingNumber,
          @ApiIgnore Authentication authentication);

  @ApiOperation(value = "Get Reports By Intern Email",
          notes = "Retrieves all reports from single internship by single user by given user email")
  List<InternReportDto> getReportsByInternEmail(
          @ApiParam(value = "Internship tracking number", required = true) String internshipTrackingNumber,
          @ApiParam(value = "Intern email", required = true) String internEmail);

  @ApiOperation(value = "Create Report",
          notes = "Creates new report by the intern")
  BaseResponseDto createReport(InternReportDto internReport,
                               @ApiIgnore Authentication authentication);

  @ApiOperation(value = "Edit Report",
          notes = "Edits intern report by the given report tracking number")
  BaseResponseDto editReport(InternReportDto internReport,
                             @ApiParam(value = "Report tracking number", required = true) String reportTrackingNumber,
                             @ApiIgnore Authentication authentication);

  @ApiOperation(value = "Remove Report",
          notes = "Removes intern report by the given report tracking number")
  BaseResponseDto removeReport(@ApiParam(value = "Report tracking number", required = true) String reportTrackingNumber,
                               @ApiIgnore Authentication authentication);
}

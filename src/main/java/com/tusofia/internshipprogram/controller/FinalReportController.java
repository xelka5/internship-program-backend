package com.tusofia.internshipprogram.controller;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.finalReport.CreateFinalReportRequestDto;
import com.tusofia.internshipprogram.dto.finalReport.CreateFinalReportResponseDto;
import com.tusofia.internshipprogram.dto.finalReport.FinalReportEmployerDto;
import com.tusofia.internshipprogram.dto.finalReport.FinalReportWithInternProfileDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Api description of final report operations controller.
 *
 * @author DCvetkov
 * @since 2020
 */
@Api(tags={"Final Report Endpoints"},  description = "Final report operations")
public interface FinalReportController {

  @ApiOperation(value = "Get Internship Report Info Employer",
          notes = "Creates new pending internship application by an intern")
  FinalReportEmployerDto getInternshipReportInfoEmployer(
          String internshipTrackingNumber,
          @ApiIgnore Authentication authentication);

  @ApiOperation(value = "Get Final Report By Internship And User",
          notes = "Creates new pending internship application by an intern")
  FinalReportWithInternProfileDto getFinalReportByInternshipAndUser(
          @ApiParam(value = "Internship tracking number", required = true) String internshipTrackingNumber,
          @ApiIgnore Authentication authentication);

  @ApiOperation(value = "Create Final Report For Employer",
          notes = "Creates final report of the finished internship from the employer perspective")
  CreateFinalReportResponseDto createFinalReportEmployer(
          CreateFinalReportRequestDto createFinalReportRequestDto,
          @ApiIgnore Authentication authentication);

  @ApiOperation(value = "Create Final Report For Intern",
          notes = "Creates final report of the finished internship from the intern perspective")
  CreateFinalReportResponseDto createFinalReportIntern(
          CreateFinalReportRequestDto createFinalReportRequestDto,
          @ApiIgnore Authentication authentication);

  @ApiOperation(value = "Upload final report",
          notes = "Uploads final report file to the server and updates report in the database by tracking number")
  BaseResponseDto uploadFinalReport(
          @ApiParam(value = "Final report file", required = true) MultipartFile finalReportFile,
          @ApiParam(value = "Report tracking number", required = true) String reportTrackingNumber);
}

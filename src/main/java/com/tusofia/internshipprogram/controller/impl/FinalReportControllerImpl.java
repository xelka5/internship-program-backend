package com.tusofia.internshipprogram.controller.impl;

import com.tusofia.internshipprogram.controller.FinalReportController;
import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.finalReport.CreateFinalReportRequestDto;
import com.tusofia.internshipprogram.dto.finalReport.CreateFinalReportResponseDto;
import com.tusofia.internshipprogram.dto.finalReport.FinalReportEmployerDto;
import com.tusofia.internshipprogram.dto.finalReport.FinalReportWithInternProfileDto;
import com.tusofia.internshipprogram.service.FinalReportService;
import com.tusofia.internshipprogram.util.authentication.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.tusofia.internshipprogram.config.OAuth2AuthServerConfig.USER_EMAIL_LABEL;

/**
 * Url mapping and implementation of {@link FinalReportController}
 *
 * @author DCvetkov
 * @since 2020
 */
@RestController
@RequestMapping("/api/final-reports")
public class FinalReportControllerImpl implements FinalReportController {

  private static final String FINAL_REPORT_FILE = "finalReportFile";
  private static final String REPORT_TRACKING_NUMBER = "reportTrackingNumber";

  private FinalReportService finalReportService;

  @Autowired
  public FinalReportControllerImpl(FinalReportService finalReportService) {
    this.finalReportService = finalReportService;
  }

  @GetMapping("/employer/{internshipTrackingNumber}")
  public FinalReportEmployerDto getInternshipReportInfoEmployer(
          @PathVariable("internshipTrackingNumber") String internshipTrackingNumber,
          Authentication authentication) {

    String userEmail = AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL);

    return finalReportService.getInternshipReportInfoEmployer(internshipTrackingNumber, userEmail);
  }

  @GetMapping("internship/{internshipTrackingNumber}")
  public FinalReportWithInternProfileDto getFinalReportByInternshipAndUser(
          @PathVariable("internshipTrackingNumber") String internshipTrackingNumber,
          Authentication authentication) {

    String userEmail = AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL);

    return finalReportService.getFinalReportByInternshipAndUser(internshipTrackingNumber, userEmail);
  }

  @PostMapping("/employer")
  public CreateFinalReportResponseDto createFinalReportEmployer(
          @Valid @RequestBody CreateFinalReportRequestDto createFinalReportRequestDto,
          Authentication authentication) {

    String userEmail = AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL);

    return finalReportService.createFinalReportEmployer(createFinalReportRequestDto, userEmail);
  }

  @PostMapping("/intern")
  public CreateFinalReportResponseDto createFinalReportIntern(
          @Valid @RequestBody CreateFinalReportRequestDto createFinalReportRequestDto,
          Authentication authentication) {

    String userEmail = AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL);

    return finalReportService.createFinalReportIntern(createFinalReportRequestDto, userEmail);
  }

  @PostMapping("/upload")
  public BaseResponseDto uploadFinalReport(@RequestParam(FINAL_REPORT_FILE) MultipartFile finalReportFile,
                                           @RequestParam(REPORT_TRACKING_NUMBER) String reportTrackingNumber) {

    return finalReportService.uploadFinalReport(finalReportFile, reportTrackingNumber);
  }
}

package com.tusofia.internshipprogram.controller;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.report.InternReportDto;
import com.tusofia.internshipprogram.service.ReportService;
import com.tusofia.internshipprogram.util.authentication.AuthenticationUtils;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tusofia.internshipprogram.config.OAuth2AuthServerConfig.USER_EMAIL_LABEL;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

  private ReportService reportService;

  public ReportController(ReportService reportService) {
    this.reportService = reportService;
  }

  @GetMapping("{reportTrackingNumber}")
  public InternReportDto getInternReport(@PathVariable String reportTrackingNumber, Authentication authentication) {
    String userEmail = AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL);

    return reportService.getInternReport(reportTrackingNumber, userEmail);
  }

  @GetMapping("internship/{internshipTrackingNumber}")
  public List<InternReportDto> getInternReportsForInternship(@PathVariable String internshipTrackingNumber, Authentication authentication) {
    String userEmail = AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL);

    return reportService.getInternReportsByInternEmail(internshipTrackingNumber, userEmail);
  }

  @GetMapping("internship/{internshipTrackingNumber}/intern/{internEmail}")
  public List<InternReportDto> getReportsByInternEmail(@PathVariable String internshipTrackingNumber,
                                                       @PathVariable String internEmail) {

    return reportService.getInternReportsByInternEmail(internshipTrackingNumber, internEmail);
  }

  @PostMapping
  public BaseResponseDto createReport(@RequestBody InternReportDto internReport, Authentication authentication) {
    String userEmail = AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL);

    return reportService.createReport(internReport, userEmail);
  }

  @PutMapping("/{reportTrackingNumber}")
  public BaseResponseDto editReport(@RequestBody InternReportDto internReport, @PathVariable String reportTrackingNumber, Authentication authentication) {
    String userEmail = AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL);

    return reportService.editReport(internReport, userEmail, reportTrackingNumber);
  }

  @DeleteMapping("/{reportTrackingNumber}")
  public BaseResponseDto removeReport(@PathVariable String reportTrackingNumber, Authentication authentication) {
    String userEmail = AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL);

    return reportService.deleteReport(reportTrackingNumber, userEmail);
  }

}
